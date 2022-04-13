package com.course.kafkaconsumer.config;

import com.course.kafkaconsumer.entity.CarLocation;
import com.course.kafkaconsumer.error.handler.GlobalErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.io.IOException;

@Configuration
public class KafkaConfig {

  @Autowired private KafkaProperties kafkaProperties;
  private static final int SECOND = 1000;
  private static final int MINUTE = SECOND * 60;
  private static final int DISTANCE_LIMIT = 100;
  private static final int RETRY_ATTEMPTS = 3;

  @Bean
  public ConsumerFactory<Object, Object> consumerFactory() {
    var properties = kafkaProperties.buildConsumerProperties();
    properties.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, MINUTE * 2);
    return new DefaultKafkaConsumerFactory<>(properties);
  }

  @Bean(name = "farLocationContainerFactory")
  public ConcurrentKafkaListenerContainerFactory<Object, Object> farLocationContainerFactory(
      ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
    var factory = new ConcurrentKafkaListenerContainerFactory<>();
    configurer.configure(factory, consumerFactory());

    factory.setRecordFilterStrategy(
        new RecordFilterStrategy<>() {

          final ObjectMapper objectMapper = new ObjectMapper();

          @Override
          public boolean filter(ConsumerRecord<Object, Object> consumerRecord) {
            try {
              var carLocation =
                  objectMapper.readValue(consumerRecord.value().toString(), CarLocation.class);

              return carLocation.getDistance() <= DISTANCE_LIMIT;
            } catch (IOException e) {
              return false;
            }
          }
        });
    return factory;
  }

  @Bean(value = "kafkaListenerContainerFactory")
  public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
      ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
    var factory = new ConcurrentKafkaListenerContainerFactory<>();
    configurer.configure(factory, consumerFactory());

    factory.setCommonErrorHandler(new GlobalErrorHandler());

    return factory;
  }

  @Bean(value = "imageRetryContainerFactory")
  public ConcurrentKafkaListenerContainerFactory<Object, Object> imageRetryContainerFactory(
      ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
    var factory = new ConcurrentKafkaListenerContainerFactory<>();
    configurer.configure(factory, consumerFactory());

    factory.setCommonErrorHandler(new GlobalErrorHandler());
    factory.setRetryTemplate(createRetryTemplate());

    return factory;
  }
  // TODO: figure out how to use this config instead above, because setRetryTemplate is deprecated
  /* @Bean
    public RetryTopicConfiguration myRetryableTopic(KafkaTemplate<String, Object> template) {
      return RetryTopicConfigurationBuilder
              .newInstance()
              .fixedBackOff(SECOND * 10)
              .maxAttempts(RETRY_ATTEMPTS)
             .create(template);
  } */

  private RetryTemplate createRetryTemplate() {
    var retryTemplate = new RetryTemplate();
    var retryPolicy = new SimpleRetryPolicy(RETRY_ATTEMPTS);
    retryTemplate.setRetryPolicy(retryPolicy);

    var backOffPolicy = new FixedBackOffPolicy();
    backOffPolicy.setBackOffPeriod(SECOND * 10);

    retryTemplate.setBackOffPolicy(backOffPolicy);
    return retryTemplate;
  }
}

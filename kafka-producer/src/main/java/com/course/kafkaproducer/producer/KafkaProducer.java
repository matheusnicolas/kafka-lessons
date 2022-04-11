package com.course.kafkaproducer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

  private static final String KAFKA_TOPIC_NAME = "t_hello";
  @Autowired private KafkaTemplate<String, String> kafkaTemplate;

  public void sendMessage(String name) {
    kafkaTemplate.send(KAFKA_TOPIC_NAME, "Hello " + name);
  }
}

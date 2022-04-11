package com.course.kafkaproducer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaKeyProducer {

  @Autowired private KafkaTemplate<String, String> kafkaTemplate;
  private static final String TOPIC_MULTI_PARTITIONS = "t_multi_partitions";

  public void send(String key, String data) {
    kafkaTemplate.send(TOPIC_MULTI_PARTITIONS, key, data);
  }
}

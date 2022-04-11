package com.course.kafkaproducer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RebalanceProducer {

  @Autowired private KafkaTemplate<String, String> kafkaTemplate;
  private static final String TOPIC_REBALANCE = "t_rebalance";
  private int index = 0;

  @Scheduled(fixedRate = 1000)
  public void sendMessage() {
    index++;
    kafkaTemplate.send(TOPIC_REBALANCE, "Counter is " + index);
  }
}

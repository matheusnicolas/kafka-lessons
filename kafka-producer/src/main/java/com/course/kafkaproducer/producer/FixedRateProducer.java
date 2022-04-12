package com.course.kafkaproducer.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FixedRateProducer {

  @Autowired private KafkaTemplate<String, String> kafkaTemplate;

  private static final String FIXED_RATE_TOPIC = "t_fixedrate_2";
  private int index = 0;
  private Logger log = LoggerFactory.getLogger(FixedRateProducer.class);

  //@Scheduled(fixedRate = 1000)
  public void sendMessage() {
    index++;
    log.info("Index value: {}.", index);
    kafkaTemplate.send(FIXED_RATE_TOPIC, "Fixed rate: " + index);
  }
}

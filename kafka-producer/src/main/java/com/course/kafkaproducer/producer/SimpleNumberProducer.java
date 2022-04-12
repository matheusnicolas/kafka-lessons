package com.course.kafkaproducer.producer;

import com.course.kafkaproducer.entity.SimpleNumber;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SimpleNumberProducer {

  private static final String TOPIC_SIMPLE_NUMBER = "t_simple_number";
  @Autowired private KafkaTemplate<String, String> kafkaTemplate;
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public void send(SimpleNumber simpleNumber) throws JsonProcessingException {
    var json = objectMapper.writeValueAsString(simpleNumber);
    kafkaTemplate.send(TOPIC_SIMPLE_NUMBER, json);
  }
}

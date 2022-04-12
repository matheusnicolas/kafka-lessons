package com.course.kafkaproducer.producer;

import com.course.kafkaproducer.entity.FoodOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FoodOrderProducer {

  private static final String TOPIC_FOOD_ORDER = "t_food_order";
  @Autowired private KafkaTemplate<String, String> kafkaTemplate;
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public void send(FoodOrder foodOrder) throws JsonProcessingException {
    var json = objectMapper.writeValueAsString(foodOrder);
    kafkaTemplate.send(TOPIC_FOOD_ORDER, json);
  }
}

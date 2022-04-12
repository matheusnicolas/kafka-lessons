package com.course.kafkaproducer.producer;

import com.course.kafkaproducer.entity.CarLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CarLocationProducer {
  private static final String TOPIC_LOCATION = "t_location";
  @Autowired private KafkaTemplate<String, String> kafkaTemplate;
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public void send(CarLocation carLocation) throws JsonProcessingException {
    var json = objectMapper.writeValueAsString(carLocation);
    kafkaTemplate.send(TOPIC_LOCATION, carLocation.getCarId(), json);
  }
}

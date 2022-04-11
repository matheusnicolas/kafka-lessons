package com.course.kafkaproducer.producer;

import com.course.kafkaproducer.entity.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeJsonProducer {

  private static final String TOPIC_EMPLOYEE = "t_employee";
  @Autowired private KafkaTemplate<String, String> kafkaTemplate;
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public void sendMessage(Employee employee) throws JsonProcessingException {
    var json = objectMapper.writeValueAsString(employee);
    kafkaTemplate.send(TOPIC_EMPLOYEE, json);
  }
}

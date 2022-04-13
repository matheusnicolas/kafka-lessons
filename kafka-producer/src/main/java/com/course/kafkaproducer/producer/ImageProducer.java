package com.course.kafkaproducer.producer;

import com.course.kafkaproducer.entity.Image;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ImageProducer {
  private static final String TOPIC_IMAGE = "t_image";
  @Autowired private KafkaTemplate<String, String> kafkaTemplate;
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public void send(Image image) throws JsonProcessingException {
    var json = objectMapper.writeValueAsString(image);
    kafkaTemplate.send(TOPIC_IMAGE, image.getType(), json);
  }
}

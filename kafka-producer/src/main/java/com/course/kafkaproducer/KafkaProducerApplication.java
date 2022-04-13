package com.course.kafkaproducer;

import com.course.kafkaproducer.entity.Employee;
import com.course.kafkaproducer.entity.FoodOrder;
import com.course.kafkaproducer.entity.SimpleNumber;
import com.course.kafkaproducer.producer.EmployeeJsonProducer;
import com.course.kafkaproducer.producer.FoodOrderProducer;
import com.course.kafkaproducer.producer.ImageProducer;
import com.course.kafkaproducer.producer.SimpleNumberProducer;
import com.course.kafkaproducer.service.ImageService;
import com.course.kafkaproducer.util.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;

@SpringBootApplication
// @EnableScheduling
public class KafkaProducerApplication implements CommandLineRunner {


  @Autowired ImageService imageService;
  @Autowired ImageProducer imageProducer;

  public static void main(String[] args) {
    SpringApplication.run(KafkaProducerApplication.class, args);
  }

  public void run(String... args) throws Exception {
    var image = imageService.generateImage("jpg");
    var image2 = imageService.generateImage("svg");
    var image3 = imageService.generateImage("png");

    imageProducer.send(image);
    imageProducer.send(image2);
    imageProducer.send(image3);
  }

  /* For autowired ImageProducer and ImageService use this method override
  @Override
  public void run(String... args) throws Exception {
    var image = imageService.generateImage("jpg");
    var image2 = imageService.generateImage("svg");
    var image3 = imageService.generateImage("png");

    imageProducer.send(image);
    imageProducer.send(image2);
    imageProducer.send(image3);
  }


  For autowired FoodOrderProducer use this method override
  @Override
  public void run(String... args) throws Exception {
    var chickenOrder = new FoodOrder(3, "Chicken");
    var fishOrder = new FoodOrder(10, "Fish");
    var pizzaOrder = new FoodOrder(5, "Pizza");

    producer.send(chickenOrder);
    producer.send(fishOrder);
    producer.send(pizzaOrder);

    for (int i = 100; i < 103; i++) {
      var simpleNumber = new SimpleNumber(i);
      numberProducer.send(simpleNumber);
    }
  }

  For autowired EmployeeJsonProducer topic use this method override
  @Override
  public void run(String... args) throws Exception {
    for (int i = 0; i < 5; i++) {
      String date = DateConverter.convertLocalDateToString(LocalDate.now());
      var employee = new Employee("emp-" + i, "Employee " + i, LocalDate.now());
      producer.sendMessage(employee);
    }
  }

  For autowired KafkaKeyProducer topic use this method override
  @Override
  public void run(String... args) throws Exception {
    for (int i = 0; i < 10000; i++) {
      var key = "key-" + i % 4;
      var data = "data " + i + " with key " + key;
      kafkaKeyProducer.send(key, data);

      Thread.sleep(500);
    }
  }
    */
}

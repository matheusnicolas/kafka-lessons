package com.course.kafkaproducer.scheduler;

import com.course.kafkaproducer.entity.CarLocation;
import com.course.kafkaproducer.producer.CarLocationProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CarLocationScheduler {
  private static final Logger log = LoggerFactory.getLogger(CarLocationScheduler.class);

  private final CarLocation lightningMcQueen;
  private final CarLocation mater;
  private final CarLocation filmore;

  @Autowired private CarLocationProducer producer;

  public CarLocationScheduler() {
    var now = System.currentTimeMillis();

    lightningMcQueen = new CarLocation("lightning-mcqueen", now, 0);
    mater = new CarLocation("mater", now, 110);
    filmore = new CarLocation("filmore", now, 95);
  }

  //@Scheduled(fixedRate = 10000)
  public void generateCarLocation() {
    var now = System.currentTimeMillis();

    lightningMcQueen.setTimestamp(now);
    mater.setTimestamp(now);
    filmore.setTimestamp(now);

    lightningMcQueen.setDistance(lightningMcQueen.getDistance() + 1);
    mater.setDistance(mater.getDistance() - 1);
    filmore.setDistance(filmore.getDistance() + 1);

    try {
      producer.send(lightningMcQueen);
      log.info("Sent: {}", lightningMcQueen);

      producer.send(mater);
      log.info("Sent: {}", mater);

      producer.send(filmore);
      log.info("Sent: {}", filmore);
    } catch (Exception e) {
      log.warn("Error: {}", e.getMessage());
    }
  }
}

package com.course.kafkaproducer.scheduler;

import com.course.kafkaproducer.entity.Commodity;
import com.course.kafkaproducer.producer.CommodityProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CommodityScheduler {

  private static final RestTemplate restTemplate = new RestTemplate();
  private static final String API_COMMODITY_ENDPOINT = "http://localhost:8080/api/commodity/v1/all";

  @Autowired private CommodityProducer producer;

  //@Scheduled(fixedRate = 5000)
  public void fetchCommodities() {
    var commodities =
        restTemplate
            .exchange(
                API_COMMODITY_ENDPOINT,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Commodity>>() {})
            .getBody();

    assert commodities != null;
    commodities.forEach(
        commodity -> {
          try {
            producer.sendMessage(commodity);
          } catch (JsonProcessingException e) {
            e.printStackTrace();
          }
        });
  }
}

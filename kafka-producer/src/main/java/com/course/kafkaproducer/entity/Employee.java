package com.course.kafkaproducer.entity;

import com.course.kafkaproducer.json.CustomLocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
  @JsonProperty("employee_id")
  private String employeeId;

  private String name;

  @JsonProperty("birth_date")
  @JsonSerialize(using = CustomLocalDateSerializer.class)
  private LocalDate birthDate;
}

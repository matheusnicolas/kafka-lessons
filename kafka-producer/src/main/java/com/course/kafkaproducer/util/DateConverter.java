package com.course.kafkaproducer.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter {

  public static String convertLocalDateToString(LocalDate localDate) {
    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
    return localDate.format(formatters);
  }
}

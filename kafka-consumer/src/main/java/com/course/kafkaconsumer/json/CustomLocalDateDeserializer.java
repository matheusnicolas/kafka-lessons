package com.course.kafkaconsumer.json;

import java.io.IOException;
import java.io.Serial;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomLocalDateDeserializer extends StdDeserializer<LocalDate> {

  @Serial
  private static final long serialVersionUID = 1L;
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-uuuu");

  public CustomLocalDateDeserializer() {
    super(LocalDate.class);
  }

  @Override
  public LocalDate deserialize(JsonParser parser, DeserializationContext context)
      throws IOException {
    return LocalDate.parse(parser.readValueAs(String.class), formatter);
  }
}

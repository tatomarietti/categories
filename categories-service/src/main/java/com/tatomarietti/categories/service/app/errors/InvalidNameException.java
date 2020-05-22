package com.tatomarietti.categories.service.app.errors;

import lombok.Value;

@Value
public class InvalidNameException extends IllegalArgumentException {

  public InvalidNameException(final String message) {
    super(message);
  }
}

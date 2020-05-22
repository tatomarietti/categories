package com.tatomarietti.categories.service.app.errors;

import lombok.Value;

@Value
public class InvalidActionException  extends IllegalArgumentException {

  public InvalidActionException(final String message) {
    super(message);
  }
}

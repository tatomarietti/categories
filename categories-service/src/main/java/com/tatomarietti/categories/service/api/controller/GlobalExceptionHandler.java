package com.tatomarietti.categories.service.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
class GlobalExceptionHandler {

  /**
   * Handler for any other Exception.
   */
  @ExceptionHandler(value = Exception.class)
  public String defaultErrorHandler(HttpServletRequest req, Exception ex) {
    log.error(ex.getMessage());
    return ex.getMessage();
  }
}

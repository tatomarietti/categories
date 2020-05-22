package com.tatomarietti.categories.service.app.model;

import com.tatomarietti.categories.service.app.errors.InvalidNameException;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value
public class Category {

  private final String name;

  public Category(final String name) {
    if(StringUtils.isBlank(name)) {
      throw new InvalidNameException("Category name cannot be null or empty");
    }
    if(!StringUtils.isAllUpperCase(name)) {
      throw new InvalidNameException("Category name should contain only uppercase characters");
    }
    this.name = name;
  }
}

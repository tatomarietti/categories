package com.tatomarietti.categories.service.app.model;

import com.tatomarietti.categories.service.app.errors.InvalidNameException;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value
public class SubCategory {

  private final String name;

  public SubCategory(final String name) {
    if (StringUtils.isBlank(name)) {
      throw new InvalidNameException("SubCategory name cannot be null or empty");
    }
    this.name = name;
  }
}

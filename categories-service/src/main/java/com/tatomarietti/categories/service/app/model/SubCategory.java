package com.tatomarietti.categories.service.app.model;

import com.tatomarietti.categories.service.app.errors.InvalidNameException;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value
public class SubCategory {

  private final String name;

  public SubCategory(final String name) {
    if (!isValidName(name)) {
      throw new InvalidNameException("SubCategory name cannot be null or empty");
    }
    this.name = name.trim();
  }

  public static boolean isValidName(final String name) {
    return !StringUtils.isBlank(name);
  }

  public static SubCategory fromName(final String subCategory) {
    return new SubCategory(subCategory);
  }
}

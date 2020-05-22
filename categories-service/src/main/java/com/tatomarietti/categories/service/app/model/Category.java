package com.tatomarietti.categories.service.app.model;

import com.tatomarietti.categories.service.app.errors.InvalidNameException;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value
public class Category {

  private final String name;

  public Category(final String name) {
    if (!isValidName(name)) {
      throw new InvalidNameException("Category name cannot be null or empty and should contain only uppercase characters");
    }
    this.name = name.trim();
  }

  public static boolean isValidName(final String name) {
    return !StringUtils.isBlank(name) && StringUtils.isAllUpperCase(name.trim());
  }

  public static Category fromName(final String person) {
    return new Category(person);
  }
}

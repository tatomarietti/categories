package com.tatomarietti.categories.service.app.model;

import com.tatomarietti.categories.service.app.errors.InvalidNameException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@Entity
@NoArgsConstructor
@Setter(AccessLevel.PRIVATE)
public class Category {

  @Id
  private String name = "UNINITIALIZED";

  private Category(final String name) {
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

package com.tatomarietti.categories.service.app;

import com.tatomarietti.categories.service.app.model.Category;
import lombok.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Value
public class CategoryValidator {

  public static final Set<Category> DEFAULT_VALID_CATEGORIES = Collections.unmodifiableSet(
      new HashSet<>(Arrays.asList(
          Category.fromName("PERSON"),
          Category.fromName("PLACE"),
          Category.fromName("ANIMAL"),
          Category.fromName("COMPUTER"),
          Category.fromName("OTHER")
      )));

  private final Set<Category> validCategories;

  public CategoryValidator(){
    this.validCategories = DEFAULT_VALID_CATEGORIES;
  }

  public CategoryValidator(final Set<Category> validCategories){
    this.validCategories = validCategories;
  }

  public boolean isValidCategory(final Category category) {
    return validCategories.contains(category);
  }

  public boolean isValidName(final String name) {
    return Category.isValidName(name);
  }
}

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
    this.validCategories = DEFAULT_VALID_CATEGORIES;
  }

  public boolean isValid(final Category category) {
    return validCategories.contains(category);
  }
}

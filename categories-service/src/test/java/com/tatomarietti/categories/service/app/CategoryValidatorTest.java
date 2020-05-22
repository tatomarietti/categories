package com.tatomarietti.categories.service.app;

import com.tatomarietti.categories.service.app.model.Category;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryValidatorTest {

  // Requirement 8
  @Test
  void hasDefaultValidCategories() {
    final CategoryValidator categoryValidator = new CategoryValidator();

    assertTrue(categoryValidator.isValid(Category.fromName("PERSON")));
    assertTrue(categoryValidator.isValid(Category.fromName("PLACE")));
    assertTrue(categoryValidator.isValid(Category.fromName("ANIMAL")));
    assertTrue(categoryValidator.isValid(Category.fromName("COMPUTER")));
    assertTrue(categoryValidator.isValid(Category.fromName("OTHER")));
  }

  // Requirement 8
  @Test
  void rejectsInvalidCategory() {
    final CategoryValidator categoryValidator = new CategoryValidator();

    assertThat(categoryValidator.isValid(Category.fromName("INVALID")))
        .isFalse();
  }
}

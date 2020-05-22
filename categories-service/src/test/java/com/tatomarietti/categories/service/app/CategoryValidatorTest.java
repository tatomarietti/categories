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

    assertTrue(categoryValidator.isValidCategory(Category.fromName("PERSON")));
    assertTrue(categoryValidator.isValidCategory(Category.fromName("PLACE")));
    assertTrue(categoryValidator.isValidCategory(Category.fromName("ANIMAL")));
    assertTrue(categoryValidator.isValidCategory(Category.fromName("COMPUTER")));
    assertTrue(categoryValidator.isValidCategory(Category.fromName("OTHER")));
  }

  // Requirement 8
  @Test
  void rejectsInvalidCategory() {
    final CategoryValidator categoryValidator = new CategoryValidator();

    assertThat(categoryValidator.isValidCategory(Category.fromName("INVALID")))
        .isFalse();
  }
}

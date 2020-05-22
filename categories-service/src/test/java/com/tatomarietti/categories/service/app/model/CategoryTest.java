package com.tatomarietti.categories.service.app.model;

import com.tatomarietti.categories.service.app.errors.InvalidNameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

  @Test
  public void categoryNameCannotBeBlank() {
    String name = "";

    assertThrows(InvalidNameException.class, () -> Category.fromName(name)); ;
  }

  // Assumption 1.
  @Test
  public void categoryNameCannotContainLowercaseCharacters() {
    String name = "PERSoN";

    assertThrows(InvalidNameException.class, () -> Category.fromName(name)); ;
  }

  // Assumption 1.
  @Test
  public void categoryNameIsUppercase() {
    String name = "PERSON";

    final Category category = Category.fromName(name);

    assertEquals(name, category.getName());
  }

  // Assumption 2.
  @Test
  public void categoryNameCannotBlankCharacters() {
    String name = "PER SON";

    assertThrows(InvalidNameException.class, () -> Category.fromName(name)); ;
  }
}

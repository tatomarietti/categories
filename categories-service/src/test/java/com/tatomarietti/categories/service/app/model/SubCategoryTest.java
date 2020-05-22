package com.tatomarietti.categories.service.app.model;

import com.tatomarietti.categories.service.app.errors.InvalidNameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubCategoryTest {


  @Test
  public void subCategoryNameCannotBeBlank() {
    String name = "";

    assertThrows(InvalidNameException.class, () -> new SubCategory(name)); ;
  }

  // Assumption 3.
  @Test
  public void subCategoryNameCanContainLowercaseCharacters() {
    String name = "BobJones";

    final SubCategory subCategory = new SubCategory(name);

    assertEquals(name, subCategory.getName());
  }

  // Assumption 3.
  @Test
  public void subCategoryNamesAreCaseSensitive() {
    String nameUpperCamel = "BobJones";
    String nameLower = "bobjones";

    final SubCategory subCategoryUpperCamel = new SubCategory(nameUpperCamel);
    final SubCategory subCategoryLower = new SubCategory(nameLower);

    assertNotEquals(subCategoryUpperCamel, subCategoryLower);
  }

  // Assumption 4.
  @Test
  public void subCategoryNameCanContainBlankCharacters() {
    String name = "Bob Jones";

    final SubCategory subCategory = new SubCategory(name);

    assertEquals(name, subCategory.getName());
  }

}

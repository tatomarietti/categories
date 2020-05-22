package com.tatomarietti.categories.service.app.model;

import lombok.Value;

@Value
public class Item {

  private final Category category;
  private final SubCategory subCategory;

  public static Item of(final Category category, final SubCategory subCategory) {
    return new Item(category, subCategory);
  }
}

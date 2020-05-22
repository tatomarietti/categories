package com.tatomarietti.categories.service.app.model;

import lombok.Value;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

@Value
public class CategoriesSummary {

  private final LinkedHashSet<Item> items;
  private final Map<Category, Long> categoriesCount;
}

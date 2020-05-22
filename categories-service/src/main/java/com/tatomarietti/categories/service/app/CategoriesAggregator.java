package com.tatomarietti.categories.service.app;

import com.tatomarietti.categories.service.app.model.CategoriesSummary;
import com.tatomarietti.categories.service.app.model.Category;
import com.tatomarietti.categories.service.app.model.Item;
import lombok.Value;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;

@Value
public class CategoriesAggregator {

  private final CategoryValidator validator;

  /**
   * Aggregates a list of Items by filtering valid ones according to the defined CategoryValidator.
   *
   * Creates a map Category -> Long with the frequency of each Category ordered by descending freq.
   *
   * Requirements 9, 10, 11.
   *
   * @param items
   * @return
   */
  public CategoriesSummary aggregate(final LinkedHashSet<Item> items) {
    final LinkedHashSet<Item> validItems = items.stream()
        .filter(this::itemWithValidCategory)
        .collect(toCollection(LinkedHashSet::new));

    final Map<Category, Long> frequencies = validItems.stream()
        .collect(groupingBy(Item::getCategory, counting())) // Counted by Category
        .entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) // Ordered by value ascending
        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)); // LinkedHashMap to preserve the order

    return new CategoriesSummary(validItems, frequencies);
  }

  private boolean itemWithValidCategory(final Item item) {
    return validator.isValid(item.getCategory());
  }
}

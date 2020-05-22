package com.tatomarietti.categories.service.app;

import com.tatomarietti.categories.service.app.model.CategoriesSummary;
import com.tatomarietti.categories.service.app.model.Category;
import com.tatomarietti.categories.service.app.model.Item;
import com.tatomarietti.categories.service.app.model.SubCategory;
import org.assertj.core.data.MapEntry;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.LinkedHashSet;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CategoriesAggregatorTest {

  @Test
  public void aggregatesTheExampleWithDefaultValidator() {
    final LinkedHashSet<Item> items = Sets.newLinkedHashSet(
        new Item(Category.fromName("PERSON"), SubCategory.fromName("Bob Jones")),
        new Item(Category.fromName("PLACE"), SubCategory.fromName("Washington")),
        new Item(Category.fromName("PERSON"), SubCategory.fromName("Mary")),
        new Item(Category.fromName("COMPUTER"), SubCategory.fromName("Mac")),
        new Item(Category.fromName("OTHER"), SubCategory.fromName("Tree")),
        new Item(Category.fromName("ANIMAL"), SubCategory.fromName("Dog")),
        new Item(Category.fromName("PLACE"), SubCategory.fromName("Texas")),
        new Item(Category.fromName("FOOD"), SubCategory.fromName("Steak")),
        new Item(Category.fromName("ANIMAL"), SubCategory.fromName("Cat")),
        new Item(Category.fromName("PERSON"), SubCategory.fromName("Mac"))
    );

    final CategoryValidator validator = new CategoryValidator();
    final CategoriesAggregator aggregator = new CategoriesAggregator(validator);

    final CategoriesSummary summary = aggregator.aggregate(items);

    assertThat(summary)
        .extracting(CategoriesSummary::getItems)
        .extracting(LinkedHashSet::size)
        .isEqualTo(items.size() - 1); // FOOD is not a valid Category by default

    assertThat(summary.getCategoriesCount())
        .containsExactly(
            new AbstractMap.SimpleEntry<Category, Long>(Category.fromName("PERSON"), 3L),
            new AbstractMap.SimpleEntry<Category, Long>(Category.fromName("PLACE"), 2L),
            new AbstractMap.SimpleEntry<Category, Long>(Category.fromName("ANIMAL"), 2L),
            new AbstractMap.SimpleEntry<Category, Long>(Category.fromName("COMPUTER"), 1L),
            new AbstractMap.SimpleEntry<Category, Long>(Category.fromName("OTHER"), 1L)
        );
  }
}

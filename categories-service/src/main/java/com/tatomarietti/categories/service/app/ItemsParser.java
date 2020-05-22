package com.tatomarietti.categories.service.app;

import com.tatomarietti.categories.service.api.dto.ItemDto;
import com.tatomarietti.categories.service.app.model.Category;
import com.tatomarietti.categories.service.app.model.Item;
import com.tatomarietti.categories.service.app.model.SubCategory;

import java.util.LinkedHashSet;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

public class ItemsParser {

  /**
   * Parses a List of ItemDao into valid and non-duplicated Items and preserving its order.
   * <p>
   * Requirements 6 and 9 and 10.
   *
   * @param items
   * @return
   */
  public LinkedHashSet<Item> parseItems(final List<ItemDto> items) {
    return items.stream()
        .filter(this::isValid)
        .map(this::toItem)
        .collect(toCollection(LinkedHashSet::new));
  }

  private boolean isValid(final ItemDto itemDto) {
    return Category.isValidName(itemDto.getCategory()) &&
        SubCategory.isValidName(itemDto.getSubCategory());
  }

  private Item toItem(final ItemDto itemDto) {
    final Category category = Category.fromName(itemDto.getCategory());
    final SubCategory subCategory = SubCategory.fromName(itemDto.getSubCategory());
    return Item.of(category, subCategory);
  }
}

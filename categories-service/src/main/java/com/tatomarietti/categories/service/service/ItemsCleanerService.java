package com.tatomarietti.categories.service.service;

import com.tatomarietti.categories.service.api.dto.CategoriesSummaryDto;
import com.tatomarietti.categories.service.api.dto.CategoryDto;
import com.tatomarietti.categories.service.api.dto.ItemDto;
import com.tatomarietti.categories.service.app.ItemsParser;
import com.tatomarietti.categories.service.app.model.CategoriesSummary;
import com.tatomarietti.categories.service.app.model.Category;
import com.tatomarietti.categories.service.app.model.Item;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ItemsCleanerService {

  private CategoryService categoryService;

  /**
   * Cleans the given Items list based on the registered Categories in the system.
   *
   * @param items the list of items to clean
   * @return A Summary containing the cleaned list and a per-category frequencies list
   */
  public CategoriesSummaryDto clean(final List<ItemDto> items) {
    log.info("Cleaning items {}", items);
    final ItemsParser parser = new ItemsParser();
    final LinkedHashSet<Item> parsedItems = parser.parseItems(items);

    final CategoriesSummary summary = categoryService.summarize(parsedItems);
    log.info("Computed summary {}", summary);
    return toSummaryDto(summary);
  }

  private CategoriesSummaryDto toSummaryDto(final CategoriesSummary summary) {
    return new CategoriesSummaryDto(toItemsDto(summary.getItems()), toCategoriesCountDto(summary.getCategoriesCount()));
  }

  private Map<CategoryDto, Long> toCategoriesCountDto(final Map<Category, Long> categoriesCount) {
    return categoriesCount.entrySet()
        .stream()
        .map(this::toCategoryDtoEntry)
        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
  }

  private Map.Entry<CategoryDto, Long> toCategoryDtoEntry(final Map.Entry<Category, Long> entry) {
    return new AbstractMap.SimpleEntry<>(categoryService.toCategoryDto(entry.getKey()), entry.getValue());
  }

  private LinkedHashSet<ItemDto> toItemsDto(final LinkedHashSet<Item> items) {
    return items.stream()
        .map(this::toItemDto)
        .collect(toCollection(LinkedHashSet::new));
  }

  private ItemDto toItemDto(final Item item) {
    return new ItemDto(item.getCategory().getName(), item.getSubCategory().getName());
  }

}

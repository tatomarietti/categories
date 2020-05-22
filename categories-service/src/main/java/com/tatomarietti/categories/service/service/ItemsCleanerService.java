package com.tatomarietti.categories.service.service;

import com.tatomarietti.categories.service.api.dto.CategoriesSummaryDto;
import com.tatomarietti.categories.service.api.dto.CategoryDto;
import com.tatomarietti.categories.service.api.dto.ItemDto;
import com.tatomarietti.categories.service.api.dto.SubCategoryDto;
import com.tatomarietti.categories.service.app.ItemsParser;
import com.tatomarietti.categories.service.app.model.CategoriesSummary;
import com.tatomarietti.categories.service.app.model.Category;
import com.tatomarietti.categories.service.app.model.Item;
import com.tatomarietti.categories.service.app.model.SubCategory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ItemsCleanerService {

  private CategoryService categoryService;

  public CategoriesSummaryDto clean(final List<ItemDto> items) {

    final ItemsParser parser = new ItemsParser();
    final LinkedHashSet<Item> parsedItems = parser.parseItems(items);

    final CategoriesSummary summary = categoryService.summarize(parsedItems);

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

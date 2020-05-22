package com.tatomarietti.categories.service.service;

import com.tatomarietti.categories.service.api.dto.CategoriesSummaryDto;
import com.tatomarietti.categories.service.api.dto.ItemDto;
import com.tatomarietti.categories.service.app.CategoriesAggregator;
import com.tatomarietti.categories.service.app.CategoryValidator;
import com.tatomarietti.categories.service.app.ItemsParser;
import com.tatomarietti.categories.service.app.model.CategoriesSummary;
import com.tatomarietti.categories.service.app.model.Item;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;

@Service
public class ItemsCleanerService {

  public CategoriesSummaryDto clean(final List<ItemDto> items) {

    final ItemsParser parser = new ItemsParser();
    final LinkedHashSet<Item> parsedItems = parser.parseItems(items);

    final CategoryValidator defaultValidator = new CategoryValidator();
    final CategoriesAggregator aggregator = new CategoriesAggregator(defaultValidator);

    final CategoriesSummary summary = aggregator.aggregate(parsedItems);

    // we could be using ModelMapper here
    return new CategoriesSummaryDto(summary.getItems(), summary.getCategoriesCount());
  }
}

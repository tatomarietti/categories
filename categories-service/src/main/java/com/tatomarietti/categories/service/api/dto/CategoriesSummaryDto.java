package com.tatomarietti.categories.service.api.dto;

import com.tatomarietti.categories.service.app.model.Category;
import com.tatomarietti.categories.service.app.model.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Map;

@Data
@AllArgsConstructor
public class CategoriesSummaryDto {

  private final LinkedHashSet<ItemDto> items;
  private final Map<CategoryDto, Long> categoriesCount;
}

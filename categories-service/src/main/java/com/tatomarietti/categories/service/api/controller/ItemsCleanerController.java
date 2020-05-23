package com.tatomarietti.categories.service.api.controller;

import com.tatomarietti.categories.service.api.dto.CategoriesSummaryDto;
import com.tatomarietti.categories.service.api.dto.ItemDto;
import com.tatomarietti.categories.service.service.ItemsCleanerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ItemsCleanerController {

  public static final String PATH_CLEANER = "/cleaner";

  private ItemsCleanerService service;

  /**
   * Handler for POST /cleaner to clean a list of Items based on the currently valid Categories
   *
   * @param items the list of Items to clean and summarize
   * @return A summary with the list of cleaned Items and a per-category frequencies list
   */
  @PostMapping(PATH_CLEANER)
  public CategoriesSummaryDto clean(final @RequestBody List<ItemDto> items) {
    return service.clean(items);
  }
}

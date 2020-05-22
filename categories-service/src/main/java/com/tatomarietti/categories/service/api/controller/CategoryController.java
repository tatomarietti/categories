package com.tatomarietti.categories.service.api.controller;

import com.fasterxml.jackson.databind.node.TextNode;
import com.tatomarietti.categories.service.api.dto.CategoriesSummaryDto;
import com.tatomarietti.categories.service.api.dto.CategoryDto;
import com.tatomarietti.categories.service.api.dto.ItemDto;
import com.tatomarietti.categories.service.app.model.Category;
import com.tatomarietti.categories.service.service.CategoryService;
import com.tatomarietti.categories.service.service.ItemsCleanerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor(onConstructor_ = @Autowired)
public class CategoryController {

  public static final String PATH_CATEGORY = "/category";

  private CategoryService service;

  @PostMapping(PATH_CATEGORY)
  @ResponseStatus(HttpStatus.CREATED)
  public CategoryDto add(final @RequestBody CategoryDto category) {
    return service.add(category);
  }

  @GetMapping(PATH_CATEGORY)
  public List<CategoryDto> list() {
    return service.listAll();
  }
}

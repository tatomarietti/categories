package com.tatomarietti.categories.service.service;

import com.tatomarietti.categories.service.api.dto.CategoryDto;
import com.tatomarietti.categories.service.app.CategoriesAggregator;
import com.tatomarietti.categories.service.app.CategoryValidator;
import com.tatomarietti.categories.service.app.errors.InvalidActionException;
import com.tatomarietti.categories.service.app.model.CategoriesSummary;
import com.tatomarietti.categories.service.app.model.Category;
import com.tatomarietti.categories.service.app.model.Item;
import com.tatomarietti.categories.service.storage.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class CategoryService {

  private CategoryRepository categoryRepository;

  /**
   * Adds a new Category as valid if its name is valid and is not already registered
   * @param categoryDto
   * @return a Dto from the created Category
   */
  public CategoryDto add(final CategoryDto categoryDto) {
    // Let invalid name format error bubble up
    final Category category = Category.fromName(categoryDto.getName());

    final CategoryValidator categoriesValidator = getCategoriesValidator();
    if (categoriesValidator.isValidCategory(category)) {
      throw new InvalidActionException("Category with name '" + categoryDto.getName() + "' already exists.");
    }

    final Category savedCategory = categoryRepository.save(category);
    return new CategoryDto(savedCategory.getName());
  }

  /**
   * List al valid registered Categories
   *
   * @return
   */
  public List<CategoryDto> listAll() {
    return categoryRepository.findAll()
        .stream()
        .map(this::toCategoryDto)
        .collect(toList());
  }

  public CategoryValidator getCategoriesValidator() {
    final Set<Category> validCategories = Collections.unmodifiableSet(new LinkedHashSet<>(categoryRepository.findAll()));
    return new CategoryValidator(validCategories);
  }

  public CategoriesSummary summarize(final LinkedHashSet<Item> parsedItems) {
    final CategoriesAggregator aggregator = new CategoriesAggregator(getCategoriesValidator());

    return aggregator.aggregate(parsedItems);
  }

  CategoryDto toCategoryDto(final Category category) {
    return new CategoryDto(category.getName());
  }
}

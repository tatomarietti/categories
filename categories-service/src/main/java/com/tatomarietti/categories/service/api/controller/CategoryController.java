package com.tatomarietti.categories.service.api.controller;

import com.fasterxml.jackson.databind.node.TextNode;
import com.tatomarietti.categories.service.api.dto.CategoriesSummaryDto;
import com.tatomarietti.categories.service.api.dto.CategoryDto;
import com.tatomarietti.categories.service.api.dto.ItemDto;
import com.tatomarietti.categories.service.app.errors.InvalidActionException;
import com.tatomarietti.categories.service.app.errors.InvalidNameException;
import com.tatomarietti.categories.service.app.model.Category;
import com.tatomarietti.categories.service.service.CategoryService;
import com.tatomarietti.categories.service.service.ItemsCleanerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@RestController
@AllArgsConstructor(onConstructor_ = @Autowired)
public class CategoryController {

  public static final String PATH_CATEGORY = "/category";

  private CategoryService service;

  /**
   * Handler for POST /category to add a new Category.
   *
   * @param category the name of the Category to be added
   * @return the new Category
   */
  @PostMapping(PATH_CATEGORY)
  @ResponseStatus(HttpStatus.CREATED)
  public CategoryDto add(final @RequestBody CategoryDto category) {
    return service.add(category);
  }

  /**
   * Handler for DELETE /category to remove an existent Category
   *
   * @param category the name of the Category to be removed
   */
  @DeleteMapping(PATH_CATEGORY)
  public void remove(final @RequestBody CategoryDto category) {
    service.remove(category);
  }

  /**
   * * Handler for GET /category to list all valid Categories
   *
   * @return the list of currently valid Categories
   */
  @GetMapping(PATH_CATEGORY)
  public List<CategoryDto> list() {
    return service.listAll();
  }

  /**
   * Handler for conflict related exceptions.
   */
  @ExceptionHandler({InvalidActionException.class})
  @ResponseStatus(HttpStatus.CONFLICT)
  public String conflict(HttpServletRequest req, Exception ex) {
    return ex.getMessage();
  }

  /**
   * Handler for invalid input related exceptions.
   */
  @ExceptionHandler({InvalidNameException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String invalidInput(HttpServletRequest req, Exception ex) {
    return ex.getMessage();
  }
}

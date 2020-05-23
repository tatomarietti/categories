package com.tatomarietti.categories.service.api.controller;

import com.tatomarietti.categories.service.api.dto.CategoryDto;
import com.tatomarietti.categories.service.app.errors.InvalidActionException;
import com.tatomarietti.categories.service.app.errors.InvalidNameException;
import com.tatomarietti.categories.service.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
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
  @org.springframework.web.bind.annotation.ExceptionHandler({InvalidActionException.class})
  @ResponseStatus(HttpStatus.CONFLICT)
  public String conflict(HttpServletRequest req, Exception ex) {
    log.error(ex.getMessage());
    return ex.getMessage();
  }

  /**
   * Handler for invalid input related exceptions.
   */
  @org.springframework.web.bind.annotation.ExceptionHandler({InvalidNameException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String invalidInput(HttpServletRequest req, Exception ex) {
    log.error(ex.getMessage());
    return ex.getMessage();
  }
}

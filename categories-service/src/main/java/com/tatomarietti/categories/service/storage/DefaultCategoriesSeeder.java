package com.tatomarietti.categories.service.storage;

import com.tatomarietti.categories.service.app.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DefaultCategoriesSeeder {

  @Autowired
  private CategoryRepository repository;

  /**
   * Saves the default categories into the db as per Requirement 8.
   * @param event
   */
  @EventListener
  public void appReady(ApplicationReadyEvent event) {
    repository.saveAll(CategoryValidator.DEFAULT_VALID_CATEGORIES);
  }
}

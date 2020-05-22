package com.tatomarietti.categories.service.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CategoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void serviceStartsWithDefaultCategories() throws Exception {
    final String defaultCategoriesJson = "[{\"name\":\"ANIMAL\"},{\"name\":\"COMPUTER\"},{\"name\":\"OTHER\"},{\"name\":\"PERSON\"},{\"name\":\"PLACE\"}]";

    mockMvc.perform(get(CategoryController.PATH_CATEGORY))
        .andExpect(status().isOk())
        .andExpect(content().json(defaultCategoriesJson))
        .andReturn();
  }

  @Test
  void canAddACategory() throws Exception {
    final String foodCategory = "{\"name\":\"FOOD\"}";

    mockMvc.perform(post(CategoryController.PATH_CATEGORY)
        .contentType(MediaType.APPLICATION_JSON)
        .content(foodCategory))
        .andExpect(status().isCreated())
        .andReturn();
  }

  @Test
  void canAddACategoryAndIsPresentInListAll() throws Exception {
    final String anotherCategory = "{\"name\":\"FOOD\"}";

    mockMvc.perform(post(CategoryController.PATH_CATEGORY)
        .contentType(MediaType.APPLICATION_JSON)
        .content(anotherCategory))
        .andExpect(status().isCreated())
        .andReturn();

    mockMvc.perform(get(CategoryController.PATH_CATEGORY))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(anotherCategory)))
        .andReturn();
  }

  @Test
  void canRemoveACategory() throws Exception {
    final String animalCategory = "{\"name\":\"ANIMAL\"}]";

    mockMvc.perform(delete(CategoryController.PATH_CATEGORY)
        .contentType(MediaType.APPLICATION_JSON)
        .content(animalCategory))
        .andExpect(status().isOk())
        .andReturn();

    mockMvc.perform(get(CategoryController.PATH_CATEGORY))
        .andExpect(status().isOk())
        .andExpect(content().string(not(containsString(animalCategory))))
        .andReturn();
  }
}

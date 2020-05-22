package com.tatomarietti.categories.service.api.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ItemsCleanerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private static final String SAMPLE_ITEMS = "[" +
      "  {\"category\": \"PERSON\", \"subCategory\": \"Bob Jones\"}," +
      "  {\"category\": \"PLACE\", \"subCategory\": \"Washington\"}," +
      "  {\"category\": \"PERSON\", \"subCategory\": \"Mary\"}," +
      "  {\"category\": \"COMPUTER\", \"subCategory\": \"Mac\"}," +
      "  {\"category\": \"PERSON\", \"subCategory\": \"Bob Jones\"}," +
      "  {\"category\": \"OTHER\", \"subCategory\": \"Tree\"}," +
      "  {\"category\": \"ANIMAL\", \"subCategory\": \"Dog\"}," +
      "  {\"category\": \"PLACE\", \"subCategory\": \"Texas\"}," +
      "  {\"category\": \"FOOD\", \"subCategory\": \"Steak\"}," +
      "  {\"category\": \"ANIMAL\", \"subCategory\": \"Cat\"}," +
      "  {\"category\": \"PERSON\", \"subCategory\": \"Mac\"}" +
      "]";

  @Test
  void canCanCleanSampleData() throws Exception {

    final String expectedCleanedList =
        "{ \"items\": [" +
            "    { \"category\": \"PERSON\", \"subCategory\": \"Bob Jones\" }," +
            "    { \"category\": \"PLACE\", \"subCategory\": \"Washington\" }," +
            "    { \"category\": \"PERSON\", \"subCategory\": \"Mary\" }," +
            "    { \"category\": \"COMPUTER\", \"subCategory\": \"Mac\" }," +
            "    { \"category\": \"OTHER\", \"subCategory\": \"Tree\" }," +
            "    { \"category\": \"ANIMAL\", \"subCategory\": \"Dog\" }," +
            "    { \"category\": \"PLACE\", \"subCategory\": \"Texas\" }," +
            "    { \"category\": \"ANIMAL\", \"subCategory\": \"Cat\" }," +
            "    { \"category\": \"PERSON\", \"subCategory\": \"Mac\" }" +
            "  ]," +
            "  \"categoriesCount\": {" +
            "    \"PERSON\":   3," +
            "    \"PLACE\":    2," +
            "    \"ANIMAL\":   2," +
            "    \"COMPUTER\": 1," +
            "    \"OTHER\":    1" +
            "  }" +
            "}";

    mockMvc.perform(post(ItemsCleanerController.PATH_CLEANER)
        .contentType(MediaType.APPLICATION_JSON)
        .content(SAMPLE_ITEMS))
        .andExpect(status().isOk())
        .andExpect(content().json(expectedCleanedList))
        .andReturn();
  }

  @Test
  void canCanCleanSampleDataWithOnlyFoodCategory() throws Exception {
    // Add FOOD
    final String foodCategory = "{\"name\":\"FOOD\"}";
    mockMvc.perform(post(CategoryController.PATH_CATEGORY)
        .contentType(MediaType.APPLICATION_JSON)
        .content(foodCategory))
        .andExpect(status().isCreated())
        .andReturn();

    // Remove any other Category
    Arrays.asList("ANIMAL", "COMPUTER", "OTHER", "PERSON", "PLACE")
        .stream()
        .forEach(this::removeCategory);

    final String expectedCleanedList =
        "{ \"items\": [" +
            "    { \"category\": \"FOOD\", \"subCategory\": \"Steak\" }" +
            "]," +
            "\"categoriesCount\": {" +
            "    \"FOOD\":   1" +
            "  }" +
            "}";

    mockMvc.perform(post(ItemsCleanerController.PATH_CLEANER)
        .contentType(MediaType.APPLICATION_JSON)
        .content(SAMPLE_ITEMS))
        .andExpect(status().isOk())
        .andExpect(content().json(expectedCleanedList))
        .andReturn();
  }

  @Test
  void canRemoveAllCategories() throws Exception {
    // Remove any other Category
    Arrays.asList("ANIMAL", "COMPUTER", "OTHER", "PERSON", "PLACE").stream()
        .forEach(this::removeCategory);

    final String expectedCleanedList =
        "{" +
            "\"items\": []," +
            "\"categoriesCount\": {}" +
            "}";

    mockMvc.perform(post(ItemsCleanerController.PATH_CLEANER)
        .contentType(MediaType.APPLICATION_JSON)
        .content(SAMPLE_ITEMS))
        .andExpect(status().isOk())
        .andExpect(content().json(expectedCleanedList))
        .andReturn();
  }

  @SneakyThrows
  private void removeCategory(final String name) {
    final String animalCategory = "{\"name\":\"" + name + "\"}]";

    mockMvc.perform(delete(CategoryController.PATH_CATEGORY)
        .contentType(MediaType.APPLICATION_JSON)
        .content(animalCategory))
        .andExpect(status().isOk())
        .andReturn();
  }
}

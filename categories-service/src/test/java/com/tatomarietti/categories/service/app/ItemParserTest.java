package com.tatomarietti.categories.service.app;

import com.tatomarietti.categories.service.api.ItemDto;
import com.tatomarietti.categories.service.app.model.Category;
import com.tatomarietti.categories.service.app.model.Item;
import com.tatomarietti.categories.service.app.model.SubCategory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemParserTest {

  // Requirement 7
  /*
  7. The data is a list of category sub-category pairs. For example, one set of data might be:
  |     Category     |     Subcategory     |
  |------------------|---------------------|
  |    PERSON        |    Bob Jones        |
  |    PLACE         |    Washington       |
  |    PERSON        |    Mary             |
  |    COMPUTER      |    Mac              |
  |    PERSON        |    Bob Jones        |
  |    OTHER         |    Tree             |
  |    ANIMAL        |    Dog              |
  |    PLACE         |    Texas            |
  |    FOOD          |    Steak            |
  |    ANIMAL        |    Cat              |
  |    PERSON        |    Mac              |
   */
  @Test
  public void parsesTheExample() {
    List<ItemDto> itemDtos = Arrays.asList(
        new ItemDto("PERSON  ", "Bob Jones "),
        new ItemDto("PLACE   ", "Washington"),
        new ItemDto("PERSON  ", "Mary      "),
        new ItemDto("COMPUTER", "Mac       "),
        new ItemDto("PERSON  ", "Bob Jones "),
        new ItemDto("OTHER   ", "Tree      "),
        new ItemDto("ANIMAL  ", "Dog       "),
        new ItemDto("PLACE   ", "Texas     "),
        new ItemDto("FOOD    ", "Steak     "),
        new ItemDto("ANIMAL  ", "Cat       "),
        new ItemDto("PERSON  ", "Mac       ")
    );

    final ItemParser itemParser = new ItemParser();
    final LinkedHashSet<Item> parsedItems = itemParser.parseItems(itemDtos);

    assertThat(parsedItems)
        .hasSize(itemDtos.size() -1); // PERSON, Bob Jones is repeated

    assertThat(parsedItems).containsExactly(
        new Item(Category.fromName("PERSON"), SubCategory.fromName("Bob Jones")),
        new Item(Category.fromName("PLACE"),SubCategory.fromName("Washington")),
        new Item(Category.fromName("PERSON"),SubCategory.fromName("Mary")),
        new Item(Category.fromName("COMPUTER"),SubCategory.fromName("Mac")),
        new Item(Category.fromName("OTHER"),SubCategory.fromName("Tree")),
        new Item(Category.fromName("ANIMAL"),SubCategory.fromName("Dog")),
        new Item(Category.fromName("PLACE"),SubCategory.fromName("Texas")),
        new Item(Category.fromName("FOOD"),SubCategory.fromName("Steak")),
        new Item(Category.fromName("ANIMAL"),SubCategory.fromName("Cat")),
        new Item(Category.fromName("PERSON"),SubCategory.fromName("Mac"))
    );
  }
}

package com.tatomarietti.categories.service.api.dto;

import com.tatomarietti.categories.service.app.model.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto {

  private CategoryDto category;
  private SubCategoryDto subCategory;
}

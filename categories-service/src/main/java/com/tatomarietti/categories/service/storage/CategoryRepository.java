package com.tatomarietti.categories.service.storage;

import com.tatomarietti.categories.service.app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {

}

package com.capellax.shoppingCart.repository;

import com.capellax.shoppingCart.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

}

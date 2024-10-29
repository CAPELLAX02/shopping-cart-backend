package com.capellax.shoppingCart.repository;

import com.capellax.shoppingCart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryNameIgnoreCase(String category);
    List<Product> findByBrandIgnoreCase(String brand);
    List<Product> findByCategoryNameAndBrand(String category, String brand);
    List<Product> findByNameIgnoreCase(String name);
    List<Product> findByBrandAndName(String brand, String name);
    Long countByBrandAndName(String brand, String name);

}

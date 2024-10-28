package com.capellax.shoppingCart.service.product;

import com.capellax.shoppingCart.model.Product;

import java.util.List;

public class ProductService implements IProductService {

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product getProductById(Long productId) {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {

    }

    @Override
    public void updateProduct(Product product, Long productId) {

    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return List.of();
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByBrandAndName(String category, String name) {
        return List.of();
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return 0L;
    }
}

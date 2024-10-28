package com.capellax.shoppingCart.service.product;

import com.capellax.shoppingCart.model.Product;
import com.capellax.shoppingCart.request.AddProductRequest;
import com.capellax.shoppingCart.request.UpdateProductRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest product);
    Product getProductById(Long productId);

    void deleteProduct(Long productId);
    Product updateProduct(UpdateProductRequest request, Long productId);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);

}

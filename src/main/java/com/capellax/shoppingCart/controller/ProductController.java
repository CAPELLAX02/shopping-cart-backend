package com.capellax.shoppingCart.controller;

import com.capellax.shoppingCart.dto.ProductDTO;
import com.capellax.shoppingCart.exceptions.ResourceNotFoundException;
import com.capellax.shoppingCart.model.Product;
import com.capellax.shoppingCart.request.AddProductRequest;
import com.capellax.shoppingCart.request.UpdateProductRequest;
import com.capellax.shoppingCart.response.ApiResponse;
import com.capellax.shoppingCart.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            List<ProductDTO> convertedProducts = productService.getConvertedProductDTOs(products);
            if (convertedProducts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No products found!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success!", convertedProducts));

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Internal Server Error!", exception));
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductById(
            @PathVariable Long productId
    ) {
        try {
            Product product = productService.getProductById(productId);
            ProductDTO productDTO = productService.convertProductToProductDTO(product);
            return ResponseEntity.ok(new ApiResponse("Success!", productDTO));

        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Product not found!", exception.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(
            @RequestBody AddProductRequest addProductRequest
    ) {
        try {
            Product newProduct = productService.addProduct(addProductRequest);
            ProductDTO productDTO = productService.convertProductToProductDTO(newProduct);
            return ResponseEntity.ok(new ApiResponse("Add product success!", productDTO));

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Internal Server Error!", exception.getMessage()));
        }
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(
            @RequestBody UpdateProductRequest updateProductRequest,
            @PathVariable Long productId
    ) {
        try {
            Product updatedProduct = productService.updateProduct(updateProductRequest, productId);
            ProductDTO productDTO = productService.convertProductToProductDTO(updatedProduct);
            return ResponseEntity.ok(new ApiResponse("Update product success!", productDTO));

        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Product not found!", exception.getMessage()));
        }
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(
            @PathVariable Long productId
    ) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(new ApiResponse("Delete product success!", productId));

        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Product not found!", exception.getMessage()));
        }
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(
            @RequestParam String brandName,
            @RequestParam String productName
    ) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No products found!", productName));
            }
            List<ProductDTO> convertedProducts = productService.getConvertedProductDTOs(products);
            return ResponseEntity.ok(new ApiResponse("Success!", convertedProducts));

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Internal Server Error!", exception.getMessage()));
        }
    }

    @GetMapping("/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(
            @RequestParam String categoryName,
            @RequestParam String brandName
    ) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, brandName);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No products found!", null));
            }
            List<ProductDTO> convertedProducts = productService.getConvertedProductDTOs(products);
            return ResponseEntity.ok(new ApiResponse("Success!", convertedProducts));

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Internal Server Error!", exception.getMessage()));
        }
    }

    @GetMapping("/by/name")
    public ResponseEntity<ApiResponse> getProductsByName(
            @RequestParam String productName
    ) {
        try {
            List<Product> products = productService.getProductsByName(productName);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No products found!", null));
            }
            List<ProductDTO> convertedProducts = productService.getConvertedProductDTOs(products);
            return ResponseEntity.ok(new ApiResponse("Success!", convertedProducts));


        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Internal Server Error!", exception.getMessage()));
        }
    }

    @GetMapping("/by/brand")
    public ResponseEntity<ApiResponse> getProductsByBrand(
            @RequestParam String brandName
    ) {
        try {
            List<Product> products = productService.getProductByBrand(brandName);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No products found!", null));
            }
            List<ProductDTO> convertedProducts = productService.getConvertedProductDTOs(products);
            return ResponseEntity.ok(new ApiResponse("Success!", convertedProducts));

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Internal Server Error!", exception.getMessage()));
        }
    }

    @GetMapping("/by/category")
    public ResponseEntity<ApiResponse> getProductsByCategory(
            @RequestParam String categoryName
    ) {
        try {
            List<Product> products = productService.getProductsByCategory(categoryName);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No products found!", null));
            }
            List<ProductDTO> convertedProducts = productService.getConvertedProductDTOs(products);
            return ResponseEntity.ok(new ApiResponse("Success!", convertedProducts));

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Internal Server Error!", exception.getMessage()));
        }
    }

    @GetMapping("/count/by/brand-and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(
            @RequestParam String brandName,
            @RequestParam String productName
    ) {
        try {
            var productCount = productService.countProductsByBrandAndName(brandName, productName);
            if (productCount == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No products found!", productName));
            }
            return ResponseEntity.ok(new ApiResponse("Product count success!", productCount));

        } catch (Exception exception) {
            return ResponseEntity.ok(new ApiResponse("Internal Server Error!", exception.getMessage()));
        }
    }


}

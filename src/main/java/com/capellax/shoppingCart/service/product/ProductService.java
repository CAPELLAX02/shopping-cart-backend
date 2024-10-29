package com.capellax.shoppingCart.service.product;

import com.capellax.shoppingCart.dto.ImageDTO;
import com.capellax.shoppingCart.dto.ProductDTO;
import com.capellax.shoppingCart.exceptions.ProductNotFoundException;
import com.capellax.shoppingCart.model.Category;
import com.capellax.shoppingCart.model.Image;
import com.capellax.shoppingCart.model.Product;
import com.capellax.shoppingCart.repository.CategoryRepository;
import com.capellax.shoppingCart.repository.ImageRepository;
import com.capellax.shoppingCart.repository.ProductRepository;
import com.capellax.shoppingCart.request.AddProductRequest;
import com.capellax.shoppingCart.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Override
    public Product addProduct(
            AddProductRequest request
    ) {
        Category category =
                Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                        .orElseGet(() -> {
                            Category newCategory = new Category(request.getCategory().getName());
                            return categoryRepository.save(newCategory);
                        });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(
            AddProductRequest request,
            Category category
    ) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(
            Long productId
    ) {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProduct(
            Long productId
    ) {
        productRepository
                .findById(productId)
                .ifPresentOrElse(
                        productRepository::delete, () -> {
                            throw new ProductNotFoundException("Product not found!");
                        });
    }

    @Override
    public Product updateProduct(
            UpdateProductRequest request,
            Long productId
    ) {
        return productRepository

                .findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));

    }

    private Product updateExistingProduct(
            Product existingProduct,
            UpdateProductRequest request
    ) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(
            String category
    ) {
        return productRepository.findByCategoryNameIgnoreCase(category);
    }

    @Override
    public List<Product> getProductByBrand(
            String brand
    ) {
        return productRepository.findByBrandIgnoreCase(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(
            String category,
            String brand
    ) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(
            String name
    ) {
        return productRepository.findByNameIgnoreCase(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(
            String brand,
            String name
    ) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(
            String brand,
            String name
    ) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public ProductDTO convertProductToProductDTO(
            Product product
    ) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDTO> imageDTOS = images.stream()
                .map(image -> modelMapper.map(image, ImageDTO.class))
                .toList();
        productDTO.setImages(imageDTOS);
        return productDTO;
    }

    @Override
    public List<ProductDTO> getConvertedProductDTOs(
            List<Product> products
    ) {
        return products.stream()
                .map(this::convertProductToProductDTO)
                .toList();
    }











}

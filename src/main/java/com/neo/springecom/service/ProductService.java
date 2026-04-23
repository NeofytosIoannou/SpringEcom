package com.neo.springecom.service;

import com.neo.springecom.model.dto.ProductImageResponse;
import com.neo.springecom.model.Product;
import com.neo.springecom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(int productId) {
        return productRepo.findById(productId).orElse(null);
    }

    @Cacheable(cacheNames = "productImages", key = "#productId", unless = "#result == null")
    public ProductImageResponse getProductImageById(int productId) {
        Product product = productRepo.findById(productId).orElse(null);
        if (product == null || product.getImageData() == null) {
            return null;
        }
        return new ProductImageResponse(product.getImageData(), product.getImageType());
    }

    @CacheEvict(cacheNames = "productImages", key = "#result.id", condition = "#result != null && #result.id != null")
    public Product addOrUpdateProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return productRepo.save(product);
    }


        @CacheEvict(cacheNames = "productImages", key = "#id")
    public void deleteProduct(int id) {
         productRepo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepo.searchProducts(keyword);
    }
}

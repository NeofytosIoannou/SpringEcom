package com.neo.springecom.controller;

import com.neo.springecom.model.Product;
import com.neo.springecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    ProductService productService;


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        return ResponseEntity.ok(productService.getAllProducts());

    }
    @GetMapping("product/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable int productId) {
        Product product = productService.getProductById(productId);

        if (product == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(product);
    }
     @GetMapping("product/{productId}/image")
     public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
         Product product=productService.getProductById(productId);
         if(product!= null && product.getImageData() != null) {
             MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
             if (product.getImageType() != null && !product.getImageType().isBlank()) {
                 try {
                     mediaType = MediaType.parseMediaType(product.getImageType());
                 } catch (IllegalArgumentException ignored) {
                     mediaType = MediaType.APPLICATION_OCTET_STREAM;
                 }
             }
             return ResponseEntity.ok()
                     .contentType(mediaType)
                     .body(product.getImageData());
         }
         else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

     }
    @PostMapping(value = "/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile){
        Product savedProduct= null;
        try {
            savedProduct = productService.addOrUpdateProduct(product,imageFile);
            return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PutMapping(value="/product/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProduct(@PathVariable int id,
                                                @RequestPart Product product,
                                                @RequestPart MultipartFile imageFile){
        Product updatedProduct = null;
        try {
            updatedProduct=productService.addOrUpdateProduct(product,imageFile);
            return new ResponseEntity<>("Updated product",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product = productService.getProductById(id);
        if(product!=null){
            productService.deleteProduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "query", required = false) String query) {
        String term = (keyword != null && !keyword.isBlank()) ? keyword : query;
        String normalized = term == null ? "" : term.trim();

        if (normalized.isEmpty()) {
            return ResponseEntity.ok(productService.getAllProducts());
        }

        List<Product> products = productService.searchProducts(normalized);
        return ResponseEntity.ok(products);
    }
}

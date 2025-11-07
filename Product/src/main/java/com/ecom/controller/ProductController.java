package com.ecom.controller;

import com.ecom.dto.ProductRequest;
import com.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products/v1")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping("/save")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.addProduct(productRequest));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id){
        return  ResponseEntity.ok(productService.getProduct(id));
    }
    @GetMapping("/all")
    public  ResponseEntity<?> getAllProducts(){
       return ResponseEntity.ok(productService.getAllProducts());
    }
    @PutMapping("/update/{id}")
    public  ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest){
        return  ResponseEntity.ok(productService.updatedProduct(id,productRequest));
    }
    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<?> deleteProduct(@PathVariable Long id){
             productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestParam String keyword){
        return  ResponseEntity.ok(productService.searchProducts(keyword));
    }
}

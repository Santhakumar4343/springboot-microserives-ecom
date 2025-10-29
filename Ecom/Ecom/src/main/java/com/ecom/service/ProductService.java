package com.ecom.service;

import com.ecom.dto.ProductRequest;
import com.ecom.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    public ProductResponse addProduct(ProductRequest productRequest) ;
    public  ProductResponse getProduct(Long id);
    public List<ProductResponse> getAllProducts();
    public ProductResponse updatedProduct(Long id, ProductRequest productRequest) ;
}

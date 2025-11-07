package com.ecom.mapper;


import com.ecom.dto.ProductRequest;
import com.ecom.dto.ProductResponse;
import com.ecom.entity.Product;

import java.util.stream.Collectors;

public class ProductMapper {
    public static Product dtoToEntity(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        return product;
    }

    public static ProductResponse entityToDto(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setCategory(product.getCategory());
        productResponse.setPrice(product.getPrice());
        productResponse.setStockQuantity(product.getStockQuantity());
        productResponse.setImageUrl(product.getImageUrl());
        return productResponse;
    }
}


package com.ecom.serviceImpl;

import com.ecom.dto.ProductRequest;
import com.ecom.dto.ProductResponse;
import com.ecom.entity.Product;
import com.ecom.mapper.ProductMapper;
import com.ecom.repository.ProductRepository;
import com.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {
         Product product =new Product();

        Product response= productRepository.save(ProductMapper.dtoToEntity(product,productRequest));
        return ProductMapper.entityToDto(response);
    }

    @Override
    public ProductResponse getProduct(Long id) {
        Product product=productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found with id"+id));
        return ProductMapper.entityToDto(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
               return productRepository.findAll().stream().map(ProductMapper::entityToDto).toList();
    }

    @Override
    public ProductResponse updatedProduct(Long id, ProductRequest productRequest) {
        Product existing=productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found with id"+id));

       Product product= productRepository.save(ProductMapper.dtoToEntity(existing,productRequest));
        return ProductMapper.entityToDto(product);
    }
}

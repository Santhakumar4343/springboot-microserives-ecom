package com.ecom.servicecommunication;

import com.ecom.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT")
public interface ProductFeignClient {

    @GetMapping("/products/v1/get/{id}")
    ProductResponse getProductById(@PathVariable Long id);
}

package com.ecom.service;

import com.ecom.dto.OrderResponse;

import java.util.Optional;

public interface OrderService {
    public Optional<OrderResponse> createOrder(Long userId) ;
}

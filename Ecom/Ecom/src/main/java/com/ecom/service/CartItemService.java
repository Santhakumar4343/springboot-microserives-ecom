package com.ecom.service;

import com.ecom.dto.CartItemRequest;

public interface CartItemService {
    public Boolean addToCart(Long userId, CartItemRequest cartItemRequest);

    boolean deleteProductFromCart(Long userId, Long productId);
}

package com.ecom.service;

import com.ecom.dto.CartItemRequest;
import com.ecom.entity.CartItem;
import com.ecom.entity.User;

import java.util.List;

public interface CartItemService {
    public Boolean addToCart(Long userId, CartItemRequest cartItemRequest);

    boolean deleteProductFromCart(Long userId, Long productId);

    List<CartItem> getFromCart(Long userId);

    void deleteByUser(Long userId);
}

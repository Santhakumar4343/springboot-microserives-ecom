package com.ecom.serviceImpl;

import com.ecom.dto.CartItemRequest;
import com.ecom.dto.ProductResponse;
import com.ecom.dto.UserResponse;
import com.ecom.entity.CartItem;
import com.ecom.repository.CartItemRepository;
import com.ecom.service.CartItemService;
import com.ecom.servicecommunication.ProductFeignClient;
import com.ecom.servicecommunication.UserFeignClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserFeignClient userFeignClient;
    private final ProductFeignClient productFeignClient;


    @Override
    public Boolean addToCart(Long userId, CartItemRequest cartItemRequest) {

        ProductResponse productOptional = productFeignClient.getProductById(cartItemRequest.getProductId());
        System.out.println("************************"+productOptional+"********"+cartItemRequest.getProductId());
        if (productOptional == null) {
            return false;
        }

        if (productOptional.getStockQuantity() < cartItemRequest.getStockQuantity()) {
            return false;
        }
        UserResponse userOptional = userFeignClient.getUser(userId);
        if (userOptional == null) {
            return false;
        }
        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, cartItemRequest.getProductId());
        if (existingCartItem != null) {
            existingCartItem.setQuantity((int) (existingCartItem.getQuantity() + cartItemRequest.getStockQuantity()));
            existingCartItem.setPrice(1000.00);
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(cartItemRequest.getProductId());
            cartItem.setQuantity(Math.toIntExact(cartItemRequest.getStockQuantity()));
            cartItem.setPrice(1000.0);
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    @Override
    public boolean deleteProductFromCart(Long userId, Long productId) {

        return cartItemRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Override
    public List<CartItem> getFromCart(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    @Override
    public void deleteByUser(Long userId) {

        cartItemRepository.deleteByUserId(userId);

    }
}

package com.ecom.serviceImpl;

import com.ecom.dto.CartItemRequest;
import com.ecom.dto.ProductResponse;
import com.ecom.dto.UserResponse;
import com.ecom.entity.CartItem;
import com.ecom.repository.CartItemRepository;
import com.ecom.service.CartItemService;
import com.ecom.servicecommunication.ProductFeignClient;
import com.ecom.servicecommunication.UserFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
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
    int attempts=0;
//    @CircuitBreaker(name = "orderService",fallbackMethod = "addToCartFallBack")
    @Retry(name = "orderService",fallbackMethod = "addToCartFallBack")
    @Override
    public Boolean addToCart(Long userId, CartItemRequest cartItemRequest) {
        System.out.println("Attempt Count"+(++attempts));
        ProductResponse productOptional = productFeignClient.getProductById(cartItemRequest.getProductId());
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
    public Boolean addToCartFallBack(Long userId,CartItemRequest cartItemRequest,Exception exception){

               exception.getStackTrace();
               return false;
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

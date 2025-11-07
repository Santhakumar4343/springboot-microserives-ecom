package com.ecom.serviceImpl;

import com.ecom.dto.CartItemRequest;
import com.ecom.entity.CartItem;
import com.ecom.repository.CartItemRepository;
import com.ecom.service.CartItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;


    @Override
    public Boolean addToCart(Long userId, CartItemRequest cartItemRequest) {

//        Optional<Product> productOptional = productRepository.findById(cartItemRequest.getProductId());
//        if (productOptional.isEmpty()) {
//            return false;
//        }
//        Product product = productOptional.get();
//        if (product.getStockQuantity() < cartItemRequest.getStockQuantity()) {
//            return false;
//        }
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (userOptional.isEmpty()) {
//            return false;
//        }
//        User user = userOptional.get();
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

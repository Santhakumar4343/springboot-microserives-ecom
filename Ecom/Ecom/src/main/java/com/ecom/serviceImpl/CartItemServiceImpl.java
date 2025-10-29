package com.ecom.serviceImpl;

import com.ecom.dto.CartItemRequest;
import com.ecom.entity.CartItem;
import com.ecom.entity.Product;
import com.ecom.entity.User;
import com.ecom.repository.CartItemRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;
import com.ecom.service.CartItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @Override
    public Boolean addToCart(Long userId, CartItemRequest cartItemRequest) {

        Optional<Product> productOptional = productRepository.findById(cartItemRequest.getProductId());
        if (productOptional.isEmpty()) {
            return false;
        }
        Product product = productOptional.get();
        if (product.getStockQuantity() < cartItemRequest.getStockQuantity()) {
            return false;
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();
        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (existingCartItem != null) {
            existingCartItem.setQuantity((int) (existingCartItem.getQuantity() + cartItemRequest.getStockQuantity()));
            existingCartItem.setPrice(product.getPrice() * existingCartItem.getQuantity());
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(Math.toIntExact(cartItemRequest.getStockQuantity()));
            cartItem.setPrice(product.getPrice() * cartItemRequest.getStockQuantity());
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    @Override
    public boolean deleteProductFromCart(Long userId, Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent() && productOptional.isPresent()) {
            cartItemRepository.deleteByUserAndProduct(userOptional.get(), productOptional.get());
            return true;
        }
        return false;
    }
}

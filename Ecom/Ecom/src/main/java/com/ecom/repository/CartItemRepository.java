package com.ecom.repository;

import com.ecom.entity.CartItem;
import com.ecom.entity.Product;
import com.ecom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    CartItem findByUserAndProduct(User user, Product product);
    void deleteByUserAndProduct(User user, Product  product);
    List<CartItem> findByUserId(Long userId);
    void deleteByUser(User user);
}

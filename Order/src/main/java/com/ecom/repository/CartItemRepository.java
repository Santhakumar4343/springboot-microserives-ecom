package com.ecom.repository;

import com.ecom.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    CartItem findByUserIdAndProductId(Long userId, Long productId);
    boolean deleteByUserIdAndProductId(Long userId, Long productId);
    List<CartItem> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}

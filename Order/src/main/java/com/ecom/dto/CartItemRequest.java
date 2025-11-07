package com.ecom.dto;

import lombok.Data;

@Data
public class CartItemRequest {
    private Long productId;
    private Long stockQuantity;
}

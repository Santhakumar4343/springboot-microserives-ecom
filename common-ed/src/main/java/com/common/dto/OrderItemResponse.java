package com.common.dto;

import lombok.Data;

@Data
public class OrderItemResponse {
    private  Long id;
    private String productName;
    private Integer quantity;
    private Double price;
}

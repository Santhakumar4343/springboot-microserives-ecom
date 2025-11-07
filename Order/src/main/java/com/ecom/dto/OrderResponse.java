package com.ecom.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private Long userId;
    private String orderStatus;
    private List<OrderItemResponse> orderItemResponseList;
    private Double price;
    private LocalDateTime createdAt;
}

package com.common.dto;

import lombok.Data;

@Data
public class ProductResponse {
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String category;
    private boolean active;
    private String imageUrl;
}

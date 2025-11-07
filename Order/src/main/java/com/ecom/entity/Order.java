package com.ecom.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Orders")
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private  Long id;
    private Long userId;
    private Double price;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus=OrderStatus.PENDING;
    @OneToMany(mappedBy = "order",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList=new ArrayList<>();
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private  LocalDateTime updatedAt;
}

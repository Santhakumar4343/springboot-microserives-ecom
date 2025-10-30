package com.ecom.controller;

import com.ecom.dto.OrderResponse;
import com.ecom.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders/v1")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestHeader("X-User-ID") Long userId){
        Optional<OrderResponse> orderResponse=orderService.createOrder(userId);
        return ResponseEntity.ok(orderResponse);
    }

}

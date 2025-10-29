package com.ecom.controller;

import com.ecom.dto.CartItemRequest;
import com.ecom.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart/v1")
public class CartItemController {
    private  final CartItemService cartItemService;

    @PostMapping("/add")
    public ResponseEntity<?>  addToCart(@RequestHeader("X-User-ID") Long userId, @RequestBody CartItemRequest cartItemRequest){
        boolean response=cartItemService.addToCart(userId,cartItemRequest);
        if(!response){
            return  ResponseEntity.badRequest().body("user not found or product not found or out of stock ");
        }
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/delete/{productId}")
    public  ResponseEntity<?> deleteFromCart(@RequestHeader("X-User-ID") Long userId,@PathVariable Long productId){
        boolean deleted =cartItemService.deleteProductFromCart(userId,productId);
        return deleted? ResponseEntity.noContent().build():ResponseEntity.badRequest().build();
    }
 }

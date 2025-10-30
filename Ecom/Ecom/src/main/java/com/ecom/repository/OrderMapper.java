package com.ecom.repository;

import com.ecom.dto.OrderItemResponse;
import com.ecom.dto.OrderResponse;
import com.ecom.entity.Order;

import java.util.stream.Collectors;

public class OrderMapper {

  public   static OrderResponse entityToDto(Order order){
        OrderResponse orderResponse=new OrderResponse();
        orderResponse.setOrderStatus(String.valueOf(order.getOrderStatus()));
        orderResponse.setPrice(order.getPrice());
        orderResponse.setUsername(order.getUser().getFirstName());
        orderResponse.setCreatedAt(order.getCreatedAt());
      // Map order items if available
      if (order.getOrderItemList() != null && !order.getOrderItemList().isEmpty()) {
          orderResponse.setOrderItemResponseList(
                  order.getOrderItemList().stream()
                          .map(item -> {
                              OrderItemResponse orderItemResponse = new OrderItemResponse();
                              orderItemResponse.setId(item.getId());
                              orderItemResponse.setProductName(item.getProduct().getName());
                              orderItemResponse.setQuantity(item.getQuantity());
                              orderItemResponse.setPrice(item.getPrice());
                              return orderItemResponse;
                          })
                          .collect(Collectors.toList())
          );
      }
        return orderResponse;
    }
}

package com.ecom.mapper;

import com.ecom.dto.OrderItemResponse;
import com.ecom.dto.OrderResponse;
import com.ecom.dto.ProductRequest;
import com.ecom.dto.ProductResponse;
import com.ecom.entity.Order;
import com.ecom.entity.Product;

import java.util.stream.Collectors;

public class ProductMapper {
  public   static Product dtoToEntity(Product product, ProductRequest productRequest){
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        return product;
    }
    public  static ProductResponse entityToDto (Product product){
      ProductResponse productResponse=new ProductResponse();
      productResponse.setName(product.getName());
      productResponse.setDescription(product.getDescription());
      productResponse.setCategory(product.getCategory());
      productResponse.setPrice(product.getPrice());
      productResponse.setStockQuantity(product.getStockQuantity());
      productResponse.setImageUrl(product.getImageUrl());
      return productResponse;
    }

    public static class OrderMapper {

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
}

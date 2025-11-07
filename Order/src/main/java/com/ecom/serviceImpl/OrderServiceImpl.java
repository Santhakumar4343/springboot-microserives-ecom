package com.ecom.serviceImpl;

import com.ecom.dto.OrderResponse;
import com.ecom.entity.CartItem;
import com.ecom.entity.Order;
import com.ecom.entity.OrderItem;
import com.ecom.entity.OrderStatus;
import com.ecom.mapper.OrderMapper;
import com.ecom.repository.OrderRepository;
import com.ecom.service.CartItemService;
import com.ecom.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartItemService cartItemService;


    @Override
    public Optional<OrderResponse> createOrder(Long userId) {
        //Check user
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (userOptional.isEmpty()) {
//          return Optional.empty();
//        }
//        User user = userOptional.get();
        //check cart items
        List<CartItem> cartItem = cartItemService.getFromCart(userId);
        if (cartItem.isEmpty()) {
         return Optional.empty();
        }
        //calculate total price
        Double totalPrice = cartItem.stream().map(CartItem::getPrice).reduce(0.0, Double::sum);

        //create order
        Order order = new Order();
        order.setUserId(userId);
        order.setPrice(totalPrice);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        List<OrderItem> orderItemList = cartItem.stream().map(
                item ->
                     new OrderItem(
                            null,
                            item.getProductId(),
                            item.getQuantity(),
                            item.getPrice(),
                            order
                    )
        ).toList();
        order.setOrderItemList(orderItemList);
       Order savedOrder= orderRepository.save(order);
        //clear cart
        cartItemService.deleteByUser(userId);
     return Optional.of(OrderMapper.entityToDto(savedOrder));
    }
}

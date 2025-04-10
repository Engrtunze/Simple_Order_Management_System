package com.system.spec.task.simple.order.management.system.service;

import com.system.spec.task.simple.order.management.system.domain.dto.CreateOrderRequest;
import com.system.spec.task.simple.order.management.system.domain.dto.OrderResponseDto;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<OrderResponseDto> getAllOrders();

    OrderResponseDto getOrderById(UUID id);

    OrderResponseDto createOrder(CreateOrderRequest orderDTO);

    List<OrderResponseDto> getOrdersByUserId(UUID userId);
}

package com.system.spec.task.simple.order.management.system.service.implementation;

import com.system.spec.task.simple.order.management.system.domain.dto.CreateOrderRequest;
import com.system.spec.task.simple.order.management.system.domain.dto.OrderItemRequestDto;
import com.system.spec.task.simple.order.management.system.domain.dto.OrderItemResponseDto;
import com.system.spec.task.simple.order.management.system.domain.dto.OrderResponseDto;
import com.system.spec.task.simple.order.management.system.domain.exception.ValidationException;
import com.system.spec.task.simple.order.management.system.domain.mappers.OrderItemMapper;
import com.system.spec.task.simple.order.management.system.domain.mappers.OrderMapper;
import com.system.spec.task.simple.order.management.system.domain.mappers.ProductMapper;
import com.system.spec.task.simple.order.management.system.domain.mappers.UserMapper;
import com.system.spec.task.simple.order.management.system.domain.models.OrderEntity;
import com.system.spec.task.simple.order.management.system.domain.models.OrderItemEntity;
import com.system.spec.task.simple.order.management.system.domain.repository.OrderItemRepository;
import com.system.spec.task.simple.order.management.system.domain.repository.OrderRepository;
import com.system.spec.task.simple.order.management.system.domain.repository.ProductRepository;
import com.system.spec.task.simple.order.management.system.domain.repository.UserRepository;
import com.system.spec.task.simple.order.management.system.service.OrderService;
import com.system.spec.task.simple.order.management.system.service.ProductService;
import com.system.spec.task.simple.order.management.system.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private  final UserService userService;
    private  final UserMapper userMapper;
    private final ProductMapper productMapper;


    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto getOrderById(UUID id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequest orderDTO) {
        var user = userService.getUserById(orderDTO.getUserId());
        if (orderDTO.getItems() == null || orderDTO.getItems().isEmpty()) {
            throw new ValidationException("Order must contain at least one item");
        }
        var  order = OrderEntity.builder()
                .user(userMapper.mapToEntityForOrder(user))
                .totalAmount(BigDecimal.ZERO)
                .build();
        List<OrderItemEntity> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequestDto itemDTO : orderDTO.getItems()) {
     var product = productService.getProductById(itemDTO.getProductId());

            if (product.getStockQuantity() < itemDTO.getQuantity()) {
                throw new ValidationException("Insufficient stock for product: " + product.getName());
            }
            var  orderItem = OrderItemEntity.builder()
                    .order(order)
                    .product(productMapper.mapToEntityFromResponse(product))
                    .quantity(itemDTO.getQuantity())
                    .price(product.getPrice())
                    .subtotal(product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())))
                    .build();

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(orderItem.getSubtotal());
            productService.updateProductStock(product.getId(), itemDTO.getQuantity());
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        var savedOrder = orderRepository.save(order);

        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public List<OrderResponseDto> getOrdersByUserId(UUID userId) {
         userService.getUserById(userId);
        return orderRepository.findByUserId(userId)
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }
}

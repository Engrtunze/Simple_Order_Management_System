package com.system.spec.task.simple.order.management.system;
import com.system.spec.task.simple.order.management.system.domain.dto.CreateOrderRequest;
import com.system.spec.task.simple.order.management.system.domain.dto.OrderItemRequestDto;
import com.system.spec.task.simple.order.management.system.domain.dto.OrderResponseDto;
import com.system.spec.task.simple.order.management.system.domain.mappers.OrderItemMapper;
import com.system.spec.task.simple.order.management.system.domain.mappers.OrderMapper;
import com.system.spec.task.simple.order.management.system.domain.models.OrderEntity;
import com.system.spec.task.simple.order.management.system.domain.models.ProductEntity;
import com.system.spec.task.simple.order.management.system.domain.models.UserEntity;
import com.system.spec.task.simple.order.management.system.domain.repository.OrderRepository;
import com.system.spec.task.simple.order.management.system.domain.repository.ProductRepository;
import com.system.spec.task.simple.order.management.system.domain.repository.UserRepository;
import com.system.spec.task.simple.order.management.system.service.ProductService;
import com.system.spec.task.simple.order.management.system.service.implementation.OrderServiceImpl;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductService productService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderItemMapper orderItemMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private UserEntity user;
    private ProductEntity product;
    private CreateOrderRequest orderDTO;
    private OrderEntity savedOrder;

    @BeforeEach
    void setUp() {
        // Setup test data
        user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setName("Test User");
        user.setEmail("test@example.com");

        product = new ProductEntity();
        product.setId(UUID.randomUUID());
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(100));
        product.setStockQuantity(10);

        var orderItemDTO = new OrderItemRequestDto();
        orderItemDTO.setProductId(UUID.randomUUID());
        orderItemDTO.setQuantity(2);

        orderDTO = new CreateOrderRequest();
        orderDTO.setUserId(UUID.randomUUID());
        orderDTO.setItems(List.of(orderItemDTO));

        savedOrder = new OrderEntity();
        savedOrder.setId(UUID.randomUUID());
        savedOrder.setUser(user);
        savedOrder.setTotalAmount(BigDecimal.valueOf(200));
    }

    @Test
    void createOrder_Success() {
        UUID userId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(savedOrder);
        // Act
        OrderResponseDto result = orderService.createOrder(orderDTO);

        // Assert
        assertNotNull(result);
        verify(productService).updateProductStock(productId, anyInt());
        verify(orderRepository).save(any(OrderEntity.class));
    }
}

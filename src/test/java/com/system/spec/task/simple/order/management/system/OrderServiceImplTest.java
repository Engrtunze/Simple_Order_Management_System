//package com.system.spec.task.simple.order.management.system;
//
//import com.system.spec.task.simple.order.management.system.domain.dto.OrderItemResponseDto;
//import com.system.spec.task.simple.order.management.system.domain.dto.OrderResponseDto;
//import com.system.spec.task.simple.order.management.system.domain.mappers.OrderMapper;
//import com.system.spec.task.simple.order.management.system.domain.models.OrderEntity;
//import com.system.spec.task.simple.order.management.system.domain.models.OrderItemEntity;
//import com.system.spec.task.simple.order.management.system.domain.models.ProductEntity;
//import com.system.spec.task.simple.order.management.system.domain.repository.OrderRepository;
//import com.system.spec.task.simple.order.management.system.domain.repository.ProductRepository;
//import com.system.spec.task.simple.order.management.system.service.OrderService;
//import java.time.Instant;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.*;
//import org.springframework.data.domain.jaxb.SpringDataJaxb;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class OrderServiceTest {
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private OrderMapper orderMapper;
//
//    @InjectMocks
//    private OrderService orderService;
//
//    private UUID orderId;
//    private UUID productId;
//    private OrderItemEntity order;
////    private SpringDataJaxb.OrderRes orderDto;
//    private ProductEntity product;
//    private OrderItemEntity orderItem;
//    private OrderItemResponseDto orderItemDto;
//    private List<OrderEntity> orders;
//    private List<OrderResponseDto> orderDtos;
//
//    @BeforeEach
//    void setUp() {
//        // Initialize test data with UUIDs
//        orderId = UUID.randomUUID();
//        productId = UUID.randomUUID();
//
//        // Set up Product
//        product = new ProductEntity();
//        product.setId(productId);
//        product.setName("Test Product");
//        product.setPrice(BigDecimal.valueOf(10.99));
//        product.setStockQuantity(100);
//
//        // Set up OrderItem
//        orderItem = new OrderItemEntity();
//        orderItem.setId(UUID.randomUUID());
//        orderItem.setProduct(product);
//        orderItem.setQuantity(2);
//        orderItem.setPrice(BigDecimal.valueOf(10.99));
//
//        // Set up Order
//        order = new OrderItemEntity();
//        order.setId(orderId);
//        order.setCreatedAt(Instant.now());
//        order.set("Test Customer");
//        order.setCustomerEmail("test@example.com");
//        order.setStatus("PENDING");
//        order.setItems(Collections.singletonList(orderItem));
//        orderItem.setOrder(order);
//
//        // Set up OrderItemDto
//        orderItemDto = new OrderItemDto();
//        orderItemDto.setProductId(productId);
//        orderItemDto.setQuantity(2);
//        orderItemDto.setPrice(BigDecimal.valueOf(10.99));
//
//        // Set up OrderDto
//        orderDto = new OrderDto();
//        orderDto.setId(orderId);
//        orderDto.setOrderDate(LocalDateTime.now());
//        orderDto.setCustomerName("Test Customer");
//        orderDto.setCustomerEmail("test@example.com");
//        orderDto.setStatus("PENDING");
//        orderDto.setItems(Collections.singletonList(orderItemDto));
//
//        // Set up lists
//        orders = new ArrayList<>();
//        orders.add(order);
//
//        orderDtos = new ArrayList<>();
//        orderDtos.add(orderDto);
//    }
//
//    @Test
//    void testCreateOrder() {
//        // Arrange
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//        when(orderMapper.toEntity(orderDto)).thenReturn(order);
//        when(orderRepository.save(any(Order.class))).thenReturn(order);
//        when(orderMapper.toDto(order)).thenReturn(orderDto);
//
//        // Act
//        OrderDto result = orderService.createOrder(orderDto);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(orderDto.getId(), result.getId());
//        assertEquals(orderDto.getCustomerName(), result.getCustomerName());
//        verify(productRepository).findById(productId);
//        verify(orderRepository).save(order);
//    }
//
//    @Test
//    void testCreateOrderWithInvalidProduct() {
//        // Arrange
//        when(productRepository.findById(productId)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(orderDto));
//        verify(productRepository).findById(productId);
//        verify(orderRepository, never()).save(any(Order.class));
//    }
//
//    @Test
//    void testCreateOrderWithInsufficientQuantity() {
//        // Arrange
//        product.setAvailableQuantity(1);  // Less than required quantity
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(orderDto));
//        verify(productRepository).findById(productId);
//        verify(orderRepository, never()).save(any(Order.class));
//    }
//
//    @Test
//    void testGetAllOrders() {
//        // Arrange
//        when(orderRepository.findAll()).thenReturn(orders);
//        when(orderMapper.toDto(order)).thenReturn(orderDto);
//
//        // Act
//        List<OrderDto> result = orderService.getAllOrders();
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(orderDto.getId(), result.get(0).getId());
//    }
//
//    @Test
//    void testGetOrderById() {
//        // Arrange
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
//        when(orderMapper.toDto(order)).thenReturn(orderDto);
//
//        // Act
//        OrderDto result = orderService.getOrderById(orderId);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(orderDto.getId(), result.getId());
//        assertEquals(orderDto.getCustomerName(), result.getCustomerName());
//    }
//
//    @Test
//    void testGetOrderByIdNotFound() {
//        // Arrange
//        UUID nonExistentId = UUID.randomUUID();
//        when(orderRepository.findById(nonExistentId)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(nonExistentId));
//    }
//
//    @Test
//    void testUpdateOrder() {
//        // Arrange
//        OrderDto updatedOrderDto = new OrderDto();
//        updatedOrderDto.setId(orderId);
//        updatedOrderDto.setCustomerName("Updated Customer");
//        updatedOrderDto.setCustomerEmail("updated@example.com");
//        updatedOrderDto.setStatus("SHIPPED");
//        updatedOrderDto.setItems(Collections.singletonList(orderItemDto));
//
//        Order updatedOrder = new Order();
//        updatedOrder.setId(orderId);
//        updatedOrder.setOrderDate(order.getOrderDate());
//        updatedOrder.setCustomerName("Updated Customer");
//        updatedOrder.setCustomerEmail("updated@example.com");
//        updatedOrder.setStatus("SHIPPED");
//        updatedOrder.setItems(Collections.singletonList(orderItem));
//
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);
//        when(orderMapper.toDto(updatedOrder)).thenReturn(updatedOrderDto);
//
//        // Act
//        OrderDto result = orderService.updateOrder(orderId, updatedOrderDto);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals("Updated Customer", result.getCustomerName());
//        assertEquals("updated@example.com", result.getCustomerEmail());
//        assertEquals("SHIPPED", result.getStatus());
//    }
//
//    @Test
//    void testUpdateOrderNotFound() {
//        // Arrange
//        UUID nonExistentId = UUID.randomUUID();
//        when(orderRepository.findById(nonExistentId)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(ResourceNotFoundException.class, () -> orderService.updateOrder(nonExistentId, orderDto));
//        verify(orderRepository, never()).save(any(Order.class));
//    }
//
//    @Test
//    void testDeleteOrder() {
//        // Arrange
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
//        doNothing().when(orderRepository).delete(order);
//
//        // Act & Assert
//        assertDoesNotThrow(() -> orderService.deleteOrder(orderId));
//        verify(orderRepository).delete(order);
//    }
//
//    @Test
//    void testDeleteOrderNotFound() {
//        // Arrange
//        UUID nonExistentId = UUID.randomUUID();
//        when(orderRepository.findById(nonExistentId)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(ResourceNotFoundException.class, () -> orderService.deleteOrder(nonExistentId));
//        verify(orderRepository, never()).delete(any(Order.class));
//    }
//
//    @Test
//    void testUpdateOrderStatus() {
//        // Arrange
//        String newStatus = "DELIVERED";
//        Order updatedOrder = new Order();
//        updatedOrder.setId(orderId);
//        updatedOrder.setOrderDate(order.getOrderDate());
//        updatedOrder.setCustomerName(order.getCustomerName());
//        updatedOrder.setCustomerEmail(order.getCustomerEmail());
//        updatedOrder.setStatus(newStatus);
//        updatedOrder.setItems(order.getItems());
//
//        OrderDto updatedDto = new OrderDto();
//        updatedDto.setId(orderId);
//        updatedDto.setOrderDate(orderDto.getOrderDate());
//        updatedDto.setCustomerName(orderDto.getCustomerName());
//        updatedDto.setCustomerEmail(orderDto.getCustomerEmail());
//        updatedDto.setStatus(newStatus);
//        updatedDto.setItems(orderDto.getItems());
//
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
//        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);
//        when(orderMapper.toDto(updatedOrder)).thenReturn(updatedDto);
//
//        // Act
//        OrderDto result = orderService.updateOrderStatus(orderId, newStatus);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(newStatus, result.getStatus());
//        verify(orderRepository).save(order);
//    }
//
//    @Test
//    void testUpdateOrderStatusNotFound() {
//        // Arrange
//        UUID nonExistentId = UUID.randomUUID();
//        when(orderRepository.findById(nonExistentId)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(ResourceNotFoundException.class, () -> orderService.updateOrderStatus(nonExistentId, "DELIVERED"));
//    }
//}
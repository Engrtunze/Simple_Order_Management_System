package com.system.spec.task.simple.order.management.system;

import com.system.spec.task.simple.order.management.system.domain.mappers.ProductMapper;
import com.system.spec.task.simple.order.management.system.domain.models.ProductEntity;
import com.system.spec.task.simple.order.management.system.domain.repository.ProductRepository;
import com.system.spec.task.simple.order.management.system.service.implementation.ProductImpl;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductImpl productService;

    private ProductEntity product;

    @BeforeEach
    void setUp() {
        product = new ProductEntity();
        product.setId(UUID.randomUUID());
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(100));
        product.setStockQuantity(10);
    }

    @Test
    void updateProductStock_Success() {
        when(productRepository.findById(UUID.randomUUID())).thenReturn(Optional.of(product));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(product);
        productService.updateProductStock(UUID.randomUUID(), 5);

        verify(productRepository).save(product);
        assert(product.getStockQuantity() == 5);
    }
}

package com.system.spec.task.simple.order.management.system.service;

import com.system.spec.task.simple.order.management.system.domain.dto.ProductRequestDto;
import com.system.spec.task.simple.order.management.system.domain.dto.ProductResponseDto;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductResponseDto> getAllProducts();

    ProductResponseDto getProductById(UUID id);

    ProductResponseDto createProduct(ProductRequestDto request);

    ProductResponseDto updateProduct(UUID id, ProductRequestDto productDTO);

    void deleteProduct(UUID id);

    void updateProductStock(UUID id, int quantity);
}

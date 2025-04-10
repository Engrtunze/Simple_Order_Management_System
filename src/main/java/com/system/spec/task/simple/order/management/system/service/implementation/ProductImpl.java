package com.system.spec.task.simple.order.management.system.service.implementation;

import com.system.spec.task.simple.order.management.system.domain.dto.ProductRequestDto;
import com.system.spec.task.simple.order.management.system.domain.dto.ProductResponseDto;
import com.system.spec.task.simple.order.management.system.domain.exception.InsufficientStockException;
import com.system.spec.task.simple.order.management.system.domain.mappers.ProductMapper;
import com.system.spec.task.simple.order.management.system.domain.repository.ProductRepository;
import com.system.spec.task.simple.order.management.system.service.ProductService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductImpl  implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .filter(product -> !product.isDeleted())
                .map(productMapper::mapTtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto getProductById(UUID id) {
        return productRepository.findById(id)
                .map(productMapper::mapTtoResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    @Override
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto request) {
        var  product = productMapper.mapToEntity(request);
        var savedProduct = productRepository.save(product);
        return productMapper.mapTtoResponse(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponseDto updateProduct(UUID id, ProductRequestDto productDTO) {
        var  product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        productMapper.updateEntityFromDTO(productDTO, product);
        var updatedProduct = productRepository.save(product);
        return productMapper.mapTtoResponse(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        var product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        product.setDeleted(true);
        productRepository.save(product);

    }

    @Override
    @Transactional
    public void updateProductStock(UUID id, int quantity) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        int newStock = product.getStockQuantity() - quantity;
        if (newStock < 0) {
            throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
        }

        product.setStockQuantity(newStock);
        productRepository.save(product);
    }
}

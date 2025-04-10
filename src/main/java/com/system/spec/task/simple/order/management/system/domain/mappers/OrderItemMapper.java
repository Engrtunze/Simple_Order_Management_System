package com.system.spec.task.simple.order.management.system.domain.mappers;

import com.system.spec.task.simple.order.management.system.domain.dto.OrderItemResponseDto;
import com.system.spec.task.simple.order.management.system.domain.models.OrderItemEntity;
import com.system.spec.task.simple.order.management.system.domain.models.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderItemResponseDto toDTO(OrderItemEntity orderItem);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderItemEntity toEntity(OrderItemResponseDto orderItemDTO);

    default OrderItemEntity toEntity(OrderItemResponseDto dto, ProductEntity product) {
        if (dto == null) {
            return null;
        }

        return OrderItemEntity.builder()
                .id(dto.getId())
                .quantity(dto.getQuantity())
                .price(product.getPrice())
                .product(product)
                .build();
    }
}

package com.system.spec.task.simple.order.management.system.domain.mappers;


import com.system.spec.task.simple.order.management.system.domain.dto.OrderResponseDto;
import com.system.spec.task.simple.order.management.system.domain.models.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.name")
    OrderResponseDto toDTO(OrderEntity order);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "items", ignore = true)
    OrderEntity toEntity(OrderResponseDto orderDTO);
}

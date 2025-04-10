package com.system.spec.task.simple.order.management.system.domain.mappers;

import com.system.spec.task.simple.order.management.system.domain.dto.ProductRequestDto;
import com.system.spec.task.simple.order.management.system.domain.dto.ProductResponseDto;
import com.system.spec.task.simple.order.management.system.domain.models.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring")
public interface ProductMapper {

    // Map FROM ProductResponseDto TO ProductEntity
    @Mapping(target = "isDeleted", source = "deleted") // Fix boolean field naming
    ProductEntity mapToEntityFromResponse(ProductResponseDto response);
    ProductEntity mapToEntity(ProductRequestDto request);
    @Mapping(target = "isDeleted", source = "deleted")
    ProductResponseDto mapTtoResponse(ProductEntity product);



    void  updateEntityFromDTO(ProductRequestDto dto, @MappingTarget ProductEntity entity);
}

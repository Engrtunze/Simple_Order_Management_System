package com.system.spec.task.simple.order.management.system.domain.mappers;


import com.system.spec.task.simple.order.management.system.domain.dto.CreateUserRequest;
import com.system.spec.task.simple.order.management.system.domain.dto.UserResponseDto;
import com.system.spec.task.simple.order.management.system.domain.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    UserEntity mapToEntityForOrder(UserResponseDto dto);
    UserEntity mapToEntity(CreateUserRequest request);
    UserResponseDto mapToResponse(UserEntity user);
    void updateEntityFromDTO(UserResponseDto dto, @MappingTarget UserEntity entity);
}

package com.system.spec.task.simple.order.management.system.domain.mappers;

import com.system.spec.task.simple.order.management.system.config.MapperConfig;
import com.system.spec.task.simple.order.management.system.domain.dto.CreateUserRequest;
import com.system.spec.task.simple.order.management.system.domain.dto.UserResponseDto;
import com.system.spec.task.simple.order.management.system.domain.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public interface UserMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    UserEntity mapToEntityForOrder(UserResponseDto dto);
    UserEntity mapToEntity(CreateUserRequest request);
    UserResponseDto mapToResponse(UserEntity user);
    void updateEntityFromDTO(UserResponseDto dto, @MappingTarget UserEntity entity);
}

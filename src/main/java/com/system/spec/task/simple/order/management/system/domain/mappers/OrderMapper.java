package com.system.spec.task.simple.order.management.system.domain.mappers;


import com.system.spec.task.simple.order.management.system.domain.dto.OrderResponseDto;
import com.system.spec.task.simple.order.management.system.domain.models.OrderEntity;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToLocalDateTime")
    OrderResponseDto toDTO(OrderEntity order);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "items", ignore = true)
    OrderEntity toEntity(OrderResponseDto orderDTO);

    @Named("instantToLocalDateTime")
    default LocalDateTime map(Instant instant) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    @Named("localDateTimeToInstant")
    default Instant map(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

}

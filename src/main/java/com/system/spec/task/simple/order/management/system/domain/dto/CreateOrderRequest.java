package com.system.spec.task.simple.order.management.system.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    @NotNull(message = "User ID is required")
    private UUID userId;
    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemRequestDto> items;
}

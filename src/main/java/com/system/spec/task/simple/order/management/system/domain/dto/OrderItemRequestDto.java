package com.system.spec.task.simple.order.management.system.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequestDto {
    @NotNull(message = "Product ID is required")
    private UUID productId;
    @NotNull(message = "Quantity is required")
    @Positive
    private Integer quantity;
}

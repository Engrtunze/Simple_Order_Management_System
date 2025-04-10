package com.system.spec.task.simple.order.management.system.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Data
@MappedSuperclass
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class AbstractAuditable  implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @NotNull
    private UUID id;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "datetime", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "datetime", nullable = false)
    private Instant updatedAt;
}

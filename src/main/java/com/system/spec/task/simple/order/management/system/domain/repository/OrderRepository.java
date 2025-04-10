package com.system.spec.task.simple.order.management.system.domain.repository;

import com.system.spec.task.simple.order.management.system.domain.models.OrderEntity;
import com.system.spec.task.simple.order.management.system.domain.models.UserEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository  extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByUserOrderByCreatedAtDesc(UserEntity user);

    List<OrderEntity> findByUserId(UUID userId);
}

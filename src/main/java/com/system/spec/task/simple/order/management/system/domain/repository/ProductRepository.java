package com.system.spec.task.simple.order.management.system.domain.repository;

import com.system.spec.task.simple.order.management.system.domain.models.ProductEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository  extends JpaRepository<ProductEntity, UUID> {
}

package com.example.ApiTesting.repository;

import com.example.ApiTesting.entity.CollectionRunItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CollectionRunItemRepository
        extends JpaRepository<CollectionRunItem, UUID> {

    List<CollectionRunItem>
    findByRunIdOrderByExecutionOrder(UUID runId);

}

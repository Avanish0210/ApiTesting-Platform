package com.example.ApiTesting.repository;

import com.example.ApiTesting.entity.CollectionRun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CollectionRunRepository extends JpaRepository<CollectionRun, UUID> {
    List<CollectionRun> findByCollectionIdOrderByStartedAtDesc(UUID collectionId);
}

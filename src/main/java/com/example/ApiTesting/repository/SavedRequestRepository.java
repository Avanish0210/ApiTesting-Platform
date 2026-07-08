package com.example.ApiTesting.repository;

import com.example.ApiTesting.entity.SavedRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SavedRequestRepository
        extends JpaRepository<SavedRequest, UUID> {

    List<SavedRequest> findByCollectionId(UUID collectionId);

}

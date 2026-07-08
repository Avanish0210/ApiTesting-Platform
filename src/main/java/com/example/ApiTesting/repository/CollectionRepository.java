package com.example.ApiTesting.repository;

import com.example.ApiTesting.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, UUID> {
    boolean existsByNameIgnoreCase(String name);

}

package com.example.ApiTesting.repository;

import com.example.ApiTesting.entity.ExtractionRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExtractionRuleRepository
        extends JpaRepository<ExtractionRule, UUID> {

    List<ExtractionRule> findBySavedRequestId(UUID requestId);

}

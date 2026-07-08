package com.example.ApiTesting.service;

import com.example.ApiTesting.repository.ExecutionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExecutionHistoryCleanupService {

    private final ExecutionHistoryRepository repository;

    @Value("${history.retention-days}")
    private long retentionDays;

    private static final Logger log =
            LoggerFactory.getLogger(
                    ExecutionHistoryCleanupService.class);

    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanup() {

        LocalDateTime cutoff =
                LocalDateTime.now().minusDays(retentionDays);

        repository.deleteByExecutedAtBefore(cutoff);
        log.info("Execution history cleanup completed.");


    }

}

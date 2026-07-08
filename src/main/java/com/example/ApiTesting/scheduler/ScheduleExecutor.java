package com.example.ApiTesting.scheduler;

import com.example.ApiTesting.dto.CollectionRunResponse;
import com.example.ApiTesting.entity.RunStatus;
import com.example.ApiTesting.entity.Schedule;
import com.example.ApiTesting.entity.ScheduleExecution;
import com.example.ApiTesting.repository.ScheduleExecutionRepository;
import com.example.ApiTesting.repository.ScheduleRepository;
import com.example.ApiTesting.service.CollectionRunnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleExecutor {
    private final ScheduleRepository scheduleRepository;
    private final CollectionRunnerService collectionRunnerService;
    private final ScheduleExecutionRepository executionRepository;


    @Scheduled(fixedDelay = 5000)
    public void executeSchedules() {
        List<Schedule> schedules =
                scheduleRepository.findByEnabledTrue();
        for (Schedule schedule : schedules) {
            if (!CronUtils.shouldRun(
                    schedule.getCronExpression(),
                    schedule.getLastRun())) {

                continue;
            }
            ScheduleExecution execution =
                    ScheduleExecution.builder()
                            .schedule(schedule)
                            .startedAt(LocalDateTime.now())
                            .status(RunStatus.RUNNING)
                            .build();

            executionRepository.save(execution);

            try {

                CollectionRunResponse response = collectionRunnerService.run(schedule.getCollection().getId());
                execution.setFinishedAt(LocalDateTime.now());

                execution.setStatus(RunStatus.COMPLETED);

                schedule.setLastRun(LocalDateTime.now());

                schedule.setNextRun(
                        CronUtils.nextRun(
                                schedule.getCronExpression()));

                scheduleRepository.save(schedule);

                executionRepository.save(execution);
            }
            catch (Exception ex) {
                execution.setFinishedAt(LocalDateTime.now());

                execution.setStatus(RunStatus.FAILED);

                execution.setMessage(ex.getMessage());

                executionRepository.save(execution);
            }
        }


    }

}

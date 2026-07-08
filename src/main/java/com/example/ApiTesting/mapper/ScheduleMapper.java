package com.example.ApiTesting.mapper;

import com.example.ApiTesting.dto.*;
import com.example.ApiTesting.entity.Collection;
import com.example.ApiTesting.entity.Schedule;
import com.example.ApiTesting.entity.ScheduleExecution;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    public Schedule toEntity(CreateScheduleRequest dto,
                             Collection collection) {

        return Schedule.builder()
                .name(dto.getName())
                .cronExpression(dto.getCronExpression())
                .enabled(true)
                .collection(collection)
                .build();
    }

    public ScheduleResponse toResponse(Schedule schedule) {

        return ScheduleResponse.builder()
                .id(schedule.getId())
                .name(schedule.getName())
                .collectionId(schedule.getCollection().getId())
                .collectionName(schedule.getCollection().getName())
                .cronExpression(schedule.getCronExpression())
                .enabled(schedule.getEnabled())
                .lastRun(schedule.getLastRun())
                .nextRun(schedule.getNextRun())
                .createdAt(schedule.getCreatedAt())
                .updatedAt(schedule.getUpdatedAt())
                .build();
    }

    public void updateEntity(UpdateScheduleRequest dto,
                             Schedule schedule) {

        if (dto.getName() != null) {
            schedule.setName(dto.getName());
        }

        if (dto.getCronExpression() != null) {
            schedule.setCronExpression(dto.getCronExpression());
        }

        if (dto.getEnabled() != null) {
            schedule.setEnabled(dto.getEnabled());
        }
    }

    public ScheduleExecutionResponse toExecutionResponse(
            ScheduleExecution execution) {

        return ScheduleExecutionResponse.builder()
                .id(execution.getId())
                .scheduleId(execution.getSchedule().getId())
                .collectionRunId(
                        execution.getCollectionRun() != null
                                ? execution.getCollectionRun().getId()
                                : null)
                .startedAt(execution.getStartedAt())
                .finishedAt(execution.getFinishedAt())
                .status(execution.getStatus())
                .message(execution.getMessage())
                .build();
    }
}

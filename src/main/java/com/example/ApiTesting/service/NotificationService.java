package com.example.ApiTesting.service;

import com.example.ApiTesting.entity.CollectionRun;
import com.example.ApiTesting.entity.NotificationConfig;
import com.example.ApiTesting.entity.Schedule;

public interface NotificationService {

    void sendExecutionReport(
            NotificationConfig config,
            CollectionRun run);

    void sendTestNotification(
            NotificationConfig config);

    void notifyCollectionRun(CollectionRun run);

}

package com.example.ApiTesting.service;

import com.example.ApiTesting.entity.CollectionRun;

public interface EmailTemplateService {
    String buildExecutionReport(CollectionRun run);
}

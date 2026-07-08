package com.example.ApiTesting.dto;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTestRequest {

    private UUID notificationConfigId;

}

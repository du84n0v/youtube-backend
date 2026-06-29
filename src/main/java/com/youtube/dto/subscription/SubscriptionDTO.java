package com.youtube.dto.subscription;

import com.youtube.enums.NotificationTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionDTO {
    @NotNull(message = "Channel ID is required")
    private String channelId;

    private NotificationTypeEnum type;
}
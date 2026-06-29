package com.youtube.dto.subscription;

import com.youtube.enums.NotificationTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionCreateDTO {
    @NotNull(message = "Channel ID is required")
    private String channelId;

    private NotificationTypeEnum type = NotificationTypeEnum.ALL;
}
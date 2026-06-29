package com.youtube.dto.subscription;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.youtube.dto.channel.ChannelShortInfoDTO;
import com.youtube.enums.NotificationTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionInfoDTO {
    private Integer id;
    private ChannelShortInfoDTO channel;
    private NotificationTypeEnum type;
    private LocalDateTime createdDate;
}

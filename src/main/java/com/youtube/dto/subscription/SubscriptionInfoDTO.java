package com.youtube.dto.subscription;

import com.youtube.dto.channel.ChannelShortInfoDTO;
import com.youtube.enums.NotificationTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionInfoDTO {
    private Integer id;
    private ChannelShortInfoDTO channel;
    private NotificationTypeEnum type;
}

package com.youtube.dto.channel;

import com.youtube.enums.GeneralStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangeChannelStatusDTO {
 String channelId;
 GeneralStatusEnum status;

}

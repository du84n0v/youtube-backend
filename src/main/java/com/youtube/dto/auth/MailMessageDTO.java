package com.youtube.dto.auth;

import com.youtube.enums.EmailCodeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailMessageDTO {
    private String toAccount;
    private String subject;
    private String body;
    private EmailCodeTypeEnum codeType;
}
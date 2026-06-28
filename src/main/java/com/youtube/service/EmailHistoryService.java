package com.youtube.service;

import com.youtube.dto.auth.MailMessageDTO;
import com.youtube.entity.EmailHistoryEntity;
import com.youtube.enums.EmailCodeTypeEnum;
import com.youtube.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailHistoryService {

    @Autowired
    private EmailHistoryRepository historyRepository;

    public void create(MailMessageDTO dto, String code, EmailCodeTypeEnum type) {
        EmailHistoryEntity history = new EmailHistoryEntity();
        history.setToEmail(dto.getToAccount());
        history.setTitle(dto.getSubject());
        history.setMessage(dto.getBody());
        history.setCode(code);
//        history.setCodeType(type)

        historyRepository.save(history);
    }

    public int getCountAfter(String toAccount, LocalDateTime from) {
        return historyRepository.countByToEmailAfter(toAccount, from);
    }

    public EmailHistoryEntity getLastHistoryByEmail(String email) {
        return historyRepository.findTopByToEmailOrderByCreatedDateDesc(email);
    }
}

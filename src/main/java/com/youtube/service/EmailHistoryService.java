package com.youtube.service;

import com.youtube.dto.auth.MailMessageDTO;
import com.youtube.dto.emailhistory.EmailHistoryDTO;
import com.youtube.dto.emailhistory.EmailHistoryFilterDTO;
import com.youtube.entity.EmailHistoryEntity;
import com.youtube.enums.EmailCodeTypeEnum;
import com.youtube.repository.EmailHistoryRepository;
import com.youtube.repository.custom.CustomEmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailHistoryService {

    @Autowired
    private EmailHistoryRepository historyRepository;
    @Autowired
    private CustomEmailHistoryRepository customHistoryRepository;

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

    public Page<EmailHistoryDTO> getList(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmailHistoryEntity> result = historyRepository.findAll(pageable);

        return new PageImpl<>(
                result.stream().map(this::entityToDto).toList(),
                pageable,
                result.getTotalElements());

    }

    private EmailHistoryDTO entityToDto(EmailHistoryEntity entity) {
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setId(entity.getId());
        dto.setToEmail(entity.getToEmail());
        dto.setTitle(entity.getTitle());
        dto.setMessage(entity.getMessage());
        dto.setCode(entity.getCode());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public Page<EmailHistoryDTO> getProfileHistory(String email, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmailHistoryEntity> pages = historyRepository.getAllByToEmail(email, pageable);

        return new PageImpl<>(
                pages.stream().map(this::entityToDto).toList(),
                pageable,
                pages.getTotalElements());
    }

    public Page<EmailHistoryDTO> filter(EmailHistoryFilterDTO dto, Integer page, Integer size) {
        Page<EmailHistoryEntity> pages = customHistoryRepository.filter(dto, page, size);

        return new PageImpl<>(
                pages.stream().map(this::entityToDto).toList(),
                PageRequest.of(page, size),
                pages.getTotalElements()
        );
    }
}

package com.youtube.repository.custom;

import com.youtube.dto.emailhistory.EmailHistoryFilterDTO;
import com.youtube.entity.EmailHistoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CustomEmailHistoryRepository {

    @Autowired
    private QueryPagination queryPagination;

    public Page<EmailHistoryEntity> filter(EmailHistoryFilterDTO dto, Integer page, Integer size) {
        StringBuilder select = new StringBuilder("SELECT eh FROM EmailHistoryEntity eh ");
        StringBuilder count = new StringBuilder("SELECT COUNT(DISTINCT eh) FROM EmailHistoryEntity eh ");
        Map<String, Object> params = new HashMap<>();
        StringBuilder filter = new StringBuilder(" WHERE 1=1 ");

        if(dto.getEmail() != null){
            filter.append(" AND eh.toEmail = :email");
            params.put("email", dto.getEmail());
        }

        if(dto.getCreatedDate() != null){
            LocalDateTime from = dto.getCreatedDate().atStartOfDay();
            LocalDateTime to = dto.getCreatedDate().atTime(LocalTime.MAX);

            filter.append(" AND eh.createdDate >= :from AND eh.createdDate <= :to ");
            params.put("from", from);
            params.put("to", to);
        }

        select.append(filter);
        count.append(filter);

        return queryPagination.getPaginationResult(
                select.toString(),
                count.toString(),
                params,
                page,
                size,
                EmailHistoryEntity.class);
    }
}

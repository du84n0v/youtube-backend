package com.youtube.dto.emailhistory;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmailHistoryFilterDTO {
    private String email;
    private LocalDate createdDate;
}

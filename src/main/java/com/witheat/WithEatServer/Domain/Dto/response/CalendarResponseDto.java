package com.witheat.WithEatServer.Domain.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CalendarResponseDto {
    private Long calendar_id;
    private String calendar_name;
    private LocalDate calendar_date;
}

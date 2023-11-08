package com.witheat.WithEatServer.Domain.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarCreateResponseDto {
    private Long calendarId;
}

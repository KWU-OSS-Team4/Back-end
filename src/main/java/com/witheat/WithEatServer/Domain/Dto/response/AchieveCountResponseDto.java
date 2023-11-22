package com.witheat.WithEatServer.Domain.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AchieveCountResponseDto {
    private int achieve_success;
    private int achieve_fail;
    private LocalDate achieve_date;
}

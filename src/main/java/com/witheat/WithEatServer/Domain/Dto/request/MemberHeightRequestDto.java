package com.witheat.WithEatServer.Domain.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberHeightRequestDto {
    private Long height;
    private LocalDate height_date;
}

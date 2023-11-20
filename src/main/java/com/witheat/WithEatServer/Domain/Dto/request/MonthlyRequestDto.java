package com.witheat.WithEatServer.Domain.Dto.request;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Data
public class MonthlyRequestDto {
    private LocalDate date_request;
}

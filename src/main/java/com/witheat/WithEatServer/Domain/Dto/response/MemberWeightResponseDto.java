package com.witheat.WithEatServer.Domain.Dto.response;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MemberWeightResponseDto {
    private Long weight_id;
    private int weight;
    private LocalDate weight_date;
}

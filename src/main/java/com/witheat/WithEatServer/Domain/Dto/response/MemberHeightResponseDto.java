package com.witheat.WithEatServer.Domain.Dto.response;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MemberHeightResponseDto {
    private Long height_id;
    private int height;
    private LocalDate height_date;
}

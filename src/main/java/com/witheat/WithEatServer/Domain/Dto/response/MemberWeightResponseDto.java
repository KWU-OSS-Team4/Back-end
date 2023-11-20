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
    private Long member_id;

    //체중 변화 그래프를 위한 생성자
    public MemberWeightResponseDto(Long member_id, Long weight_id)
    {
        this.member_id = member_id;
        this.weight_id  = weight_id;
    }
}

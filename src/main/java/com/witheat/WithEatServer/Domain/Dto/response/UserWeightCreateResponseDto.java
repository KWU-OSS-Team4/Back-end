package com.witheat.WithEatServer.Domain.Dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor

public class UserWeightCreateResponseDto {
    private Long weight_id;

    public UserWeightCreateResponseDto(Long weight_id)
    {
        this.weight_id  = weight_id;
    }
}



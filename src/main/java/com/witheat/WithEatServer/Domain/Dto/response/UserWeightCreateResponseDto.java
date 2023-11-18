package com.witheat.WithEatServer.Domain.Dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor

public class UserWeightCreateResponseDto {
    private Long weight_id;
    private Long user_id;

    public UserWeightCreateResponseDto(Long userId, Long weight_id)
    {
        this.user_id = userId;
        this.weight_id  = weight_id;
    }
}



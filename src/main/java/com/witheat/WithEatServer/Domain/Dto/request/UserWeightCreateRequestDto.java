package com.witheat.WithEatServer.Domain.Dto.request;


import lombok.Getter;

import java.time.LocalDate;

public class UserWeightCreateRequestDto {
    @Getter
    private int weight;
    private LocalDate weight_date;

}

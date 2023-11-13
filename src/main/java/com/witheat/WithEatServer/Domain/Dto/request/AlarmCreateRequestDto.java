package com.witheat.WithEatServer.Domain.Dto.request;

//Data Transfer Object

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmCreateRequestDto {
    private String user_alarm; //alarm_name
    private int user_timer; //alarm_time

}
package com.witheat.WithEatServer.Domain.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {
    private String name;
    private String email;
    private String password;
    private Boolean gender;
    private String plan_name;

// height, weight를 어디서 받아야할지 모르겠음
}

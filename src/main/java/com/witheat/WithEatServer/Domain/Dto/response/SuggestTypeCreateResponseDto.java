package com.witheat.WithEatServer.Domain.Dto.response;

import com.witheat.WithEatServer.Domain.Dto.request.SuggestTypeCreateRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
//응답
public class SuggestTypeCreateResponseDto {
    private Long suggest_Id;
    private String plan_name;

    public SuggestTypeCreateResponseDto(Long suggest_Id, String plan_name){
        this.suggest_Id = suggest_Id;
        this.plan_name= plan_name;
    }
}

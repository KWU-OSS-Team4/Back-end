package com.witheat.WithEatServer.Domain.Dto.request;

import com.witheat.WithEatServer.Domain.Dto.response.SuggestTypeCreateResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//요청
public class SuggestTypeCreateRequestDto {
    private String plan_name;
}

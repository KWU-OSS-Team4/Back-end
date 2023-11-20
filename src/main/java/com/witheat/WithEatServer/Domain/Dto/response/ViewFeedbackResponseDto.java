package com.witheat.WithEatServer.Domain.Dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ViewFeedbackResponseDto {
    private int progress_per;
    private String progress_color;
}

package com.witheat.WithEatServer.Config.JWT;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtToken {
    private String type;
    private Long memberId;
    private String accessToken;
}

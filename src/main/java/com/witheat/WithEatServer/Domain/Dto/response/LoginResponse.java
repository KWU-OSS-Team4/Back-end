package com.witheat.WithEatServer.Domain.Dto.response;

import com.witheat.WithEatServer.Config.JWT.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String name;
    private JwtToken jwtToken;
}
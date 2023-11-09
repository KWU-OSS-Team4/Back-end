package com.witheat.WithEatServer.Controller;

import com.witheat.WithEatServer.Domain.Dto.request.UserRequestDto;
import com.witheat.WithEatServer.Service.Utils.AuthService;
import com.witheat.WithEatServer.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // 회원가입
//    @PostMapping("/signup")
//    public BaseResponse signup(@RequestBody UserRequestDto userRequestDto) {
//        authService.join(userRequestDto);
//    }
}

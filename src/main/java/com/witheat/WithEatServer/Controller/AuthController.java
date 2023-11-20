package com.witheat.WithEatServer.Controller;

import com.witheat.WithEatServer.Domain.Dto.request.LoginRequestDto;
import com.witheat.WithEatServer.Domain.Dto.request.MemberRequestDto;
import com.witheat.WithEatServer.Exception.BadRequestException;
import com.witheat.WithEatServer.Service.AuthService;
import com.witheat.WithEatServer.common.BaseErrorResponse;
import com.witheat.WithEatServer.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/signup")
    public BaseResponse signup(@RequestBody MemberRequestDto memberRequestDto) {
        authService.join(memberRequestDto);
        return new BaseResponse(HttpStatus.OK.value(), "회원가입이 완료되었습니다.");
    }

    // 로그인
    @PostMapping("/login")
    public BaseResponse login(@RequestBody LoginRequestDto loginRequestDto) {
        return new BaseResponse(HttpStatus.OK.value(), "로그인 성공!",
                authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword()));

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    BaseErrorResponse handleBadRequestException(Exception exception) {
        return new BaseErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }
}

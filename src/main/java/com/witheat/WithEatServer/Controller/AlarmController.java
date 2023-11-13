package com.witheat.WithEatServer.Controller;

import com.witheat.WithEatServer.Domain.Dto.request.AlarmCreateRequestDto;
import com.witheat.WithEatServer.Domain.Dto.response.AlarmCreateResponseDto;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Service.AlarmService;
import com.witheat.WithEatServer.common.BaseErrorResponse;
import com.witheat.WithEatServer.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")

public class AlarmController {
    private final AlarmService alarmService;

    @PostMapping("/{userId}")
    public ResponseEntity<BaseResponse<AlarmCreateResponseDto>> makeAlarm (
            @RequestBody AlarmCreateRequestDto alarmCreateRequestDto,
            @PathVariable("userId") Long userId) {
        try{
            AlarmCreateResponseDto alarmCreateResponseDto =
                    alarmService.generateAlarm(userId, alarmCreateRequestDto);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(),
                            "알람이 설정되었습니다.",alarmCreateResponseDto));
        } catch(BaseException e){
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(),e.getMessage(),null));
        }
    }
}
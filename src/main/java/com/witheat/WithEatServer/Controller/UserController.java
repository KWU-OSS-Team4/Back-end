package com.witheat.WithEatServer.Controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.witheat.WithEatServer.Domain.Dto.request.CalendarCreateRequestDto;
import com.witheat.WithEatServer.Domain.Dto.response.CalendarCreateResponseDto;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Service.Utils.CalendarService;
import com.witheat.WithEatServer.common.BaseErrorResponse;
import com.witheat.WithEatServer.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final CalendarService calendarService;

    @PostMapping("/{userId}/calendar")
    public ResponseEntity<BaseResponse<CalendarCreateResponseDto>> makeCalendar (
            @RequestBody CalendarCreateRequestDto calendarCreateRequestDto,
            @PathVariable("userId") Long userId) {
        try {
            CalendarCreateResponseDto calendarCreateResponseDto
                    = calendarService.generateCalendar(userId, calendarCreateRequestDto);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(),
                            "일정(목표)가 추가되었습니다", calendarCreateResponseDto));
        } catch (BaseException e) {
        BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());
        return ResponseEntity
                .status(e.getCode())
                .body(new BaseResponse<>(e.getCode(), e.getMessage(), null));
        }
    }
}

package com.witheat.WithEatServer.Controller;

import com.witheat.WithEatServer.Domain.Dto.request.CalendarCreateRequestDto;
import com.witheat.WithEatServer.Domain.Dto.response.CalendarCreateResponseDto;
import com.witheat.WithEatServer.Domain.Dto.response.CalendarResponseDto;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Service.CalendarService;
import com.witheat.WithEatServer.Service.MemberService;
import com.witheat.WithEatServer.common.BaseErrorResponse;
import com.witheat.WithEatServer.common.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final CalendarService calendarService;
    private final MemberService memberService;

    // 로그아웃 기능
    @DeleteMapping("/{memberId}/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        return memberService.logout(request);
    }

    // 새로운 일정(목표) 추가
    @PostMapping("/{memberId}/calendar")
    public ResponseEntity<BaseResponse<CalendarCreateResponseDto>> makeCalendar (
            @RequestBody CalendarCreateRequestDto calendarCreateRequestDto,
            @PathVariable("memberId") Long memberId) {
        try {
            CalendarCreateResponseDto calendarCreateResponseDto
                    = calendarService.generateCalendar(memberId, calendarCreateRequestDto);

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

    // 사용자 목표 삭제
    @DeleteMapping("{memberId}/calendar/{calendarId}")
    public ResponseEntity<BaseResponse> deleteCalendar(@PathVariable("memberId") Long memberId,
                                                       @PathVariable("calendarId") Long calendarId) {
        try {
            calendarService.deleteCalendar(memberId, calendarId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse(HttpStatus.OK.value(), "일정(목표) 삭제 성공", null));
        } catch (BaseException e) {
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());

            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(), e.getMessage(), null));
        }
    }

    // 사용자 일정(목표) 확인
    @GetMapping("{memberId}/calendar/plan")
    public ResponseEntity<BaseResponse<List<CalendarResponseDto>>> confirmsCalendar(@PathVariable("memberId") Long memberId) {
        try {
            List<CalendarResponseDto> list = memberService.confirmCalendar(memberId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "일정(목표) 달력", list));
        } catch(BaseException e) {
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());

            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(), e.getMessage(), null));
        }
    }
}

package com.witheat.WithEatServer.Controller;

import com.witheat.WithEatServer.Domain.Dto.request.CalendarCreateRequestDto;
import com.witheat.WithEatServer.Domain.Dto.request.UserWeightCreateRequestDto;
import com.witheat.WithEatServer.Domain.Dto.response.CalendarCreateResponseDto;
import com.witheat.WithEatServer.Domain.Dto.response.CalendarResponseDto;
import com.witheat.WithEatServer.Domain.Dto.response.UserWeightCreateResponseDto;
import com.witheat.WithEatServer.Domain.entity.UserWeight;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Service.CalendarService;
import com.witheat.WithEatServer.Service.UserService;
import com.witheat.WithEatServer.common.BaseErrorResponse;
import com.witheat.WithEatServer.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final CalendarService calendarService;
    private final UserService userService;

    // 새로운 일정(목표) 추가
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

    // 사용자 목표 삭제
    @DeleteMapping("{userId}/calendar/{calendarId}")
    public ResponseEntity<BaseResponse> deleteCalendar(@PathVariable("userId") Long userId,
                                                       @PathVariable("calendarId") Long calendarId) {
        try {
            calendarService.deleteCalendar(userId, calendarId);

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
    @GetMapping("{userId}/calendar/plan")
    public ResponseEntity<BaseResponse<List<CalendarResponseDto>>> confirmsCalendar(@PathVariable("userId") Long userId) {
        try {
            List<CalendarResponseDto> list = userService.confirmCalendar(userId);

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

    //사용자 메인 페이지 몸무게 변경
    @PutMapping("{userId}/mypage/weight")
    public ResponseEntity<BaseResponse<UserWeightCreateResponseDto>> updateUserWeight (
            @PathVariable("userId") Long userId,
            @RequestBody UserWeightCreateRequestDto userWeightCreateRequestDto){
        //구현
        try{
            //몸무게 받기(업데이트)
            UserWeightCreateResponseDto userWeightCreateResponseDto
                    = userService.userWeightCreateResponseDto(userId, userWeightCreateRequestDto);

            //응답처리는 ok로 몸무게가 수정되었습니다로 하기
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "몸무게 수정되었습니다.", userWeightCreateResponseDto));

        }catch(BaseException e){
            //에러
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());

            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(), e.getMessage(), null));
        }
    }

    //몸무게 체중 변화 그래프
    @GetMapping("{userId}/mypage/{weightId}")
    public ResponseEntity<BaseResponse<List<UserWeight>>> weightChangeGraph(
            @PathVariable("userId") Long userId,
            @PathVariable("weightId") Long weightId){
        //구현
        try {
            List<UserWeightCreateResponseDto> weights = userService.getWeight(userId,weightId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), null));
        }catch (BaseException e){
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(),e.getMessage());

            return ResponseEntity.
                    status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(),e.getMessage(),null));
        }

    }

}

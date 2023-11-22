package com.witheat.WithEatServer.Controller;

import com.witheat.WithEatServer.Domain.Dto.request.AchieveStatusRequestDto;
import com.witheat.WithEatServer.Domain.Dto.response.AchieveCountResponseDto;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Service.MemberService;
import com.witheat.WithEatServer.common.BaseErrorResponse;
import com.witheat.WithEatServer.common.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 사용자 마이페이지 - 성취도 통계
@RestController
@RequestMapping("/member")
public class AchieveController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/{memberId}/mypage/achievement")
    public ResponseEntity<BaseResponse<AchieveCountResponseDto>> receiveStatus(
            @RequestBody AchieveStatusRequestDto achieveStatusRequestDto,
            @PathVariable("memberId") Long memberId) {
        try {
            AchieveCountResponseDto achieveCountResponseDto
                    = memberService.countAchieve(memberId, achieveStatusRequestDto);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(),
                            "성취도 통계를 불러왔습니다", achieveCountResponseDto));
        } catch (BaseException e) {
            BaseErrorResponse errorResponse = new BaseErrorResponse(e.getCode(), e.getMessage());
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseResponse<>(e.getCode(), e.getMessage(), null));
        }
    }
}

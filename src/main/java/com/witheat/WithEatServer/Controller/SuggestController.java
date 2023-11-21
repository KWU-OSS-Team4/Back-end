package com.witheat.WithEatServer.Controller;

import com.witheat.WithEatServer.Domain.entity.Member;
import com.witheat.WithEatServer.Repository.SuggestRepository;
import com.witheat.WithEatServer.Service.MemberService;
import com.witheat.WithEatServer.Service.SuggestService;
import com.witheat.WithEatServer.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/suggest")
public class SuggestController {
    private final SuggestRepository suggestRepository;
    private final SuggestService suggestService;
    private final MemberService memberService;

    @PostMapping("/{memberId}/{suggestId}/diey-type")
    public ResponseEntity<BaseResponse<Long>> dietType(
            @PathVariable Long memberId,
            @RequestBody Map<String, String> request
    ){
        //사용자로부터 입력받은 HTTP에서 plan_name이라는 정보를 얻어서 저장하기
        String plan_name = request.get("plan_name");

        //엔티티 부분에 저장하는 것 및 생성된 엔티티 Id 받아오기 (사용자가 suggest id)
        //를 모르기 때문
        Long suggestId = suggestService.createSuggestId(memberId, plan_name);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(),
                        "목표가 설정되었습니다.", suggestId));
    }

}

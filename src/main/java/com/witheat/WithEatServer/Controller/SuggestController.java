package com.witheat.WithEatServer.Controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.witheat.WithEatServer.Domain.entity.Member;
import com.witheat.WithEatServer.Domain.entity.Suggest;
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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/suggest")
public class SuggestController {
    private final SuggestRepository suggestRepository;
    private final SuggestService suggestService;
    private final MemberService memberService;

    @PostMapping("/{memberId}/{suggestId}/select")
    public ResponseEntity<BaseResponse<String>> dietType(
            @PathVariable Long memberId,
            @RequestBody Map<String, String> request
    ){
        //사용자로부터 입력받은 HTTP에서 plan_name이라는 정보를 얻어서 저장하기
        String plan_name = request.get("plan_name");

        //엔티티 부분에 저장하는 것 및 생성된 엔티티 Id 받아오기 (사용자가 suggest id)
        //를 모르기 때문
        suggestService.createSuggestId(memberId, plan_name);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(),
                        "목표가 설정되었습니다.", null));
    }

    @GetMapping("/{memberId}/userInfo")
    public ResponseEntity<Map<String,String>> nutrientInfo(
            @PathVariable Long memberId
    ){
        Suggest suggest = suggestService.calNutuient(memberId);

         Map<String, String> response = new HashMap<>();

         response.put("protein_today", String.valueOf(suggest.getProtein()));
         response.put("carbonhydrate_today", String.valueOf(suggest.getCarbohydrate()));
         response.put("fat_today", String.valueOf(suggest.getFat()));
         response.put("calorie_today",String.valueOf(suggest.getTotalKcal()));

         return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Map<String,String>> dietSuggest(
            @PathVariable Long memberId
    ){
        Suggest suggest = suggestService.getMealPlan(memberId);
        String plan_name = suggest.getPlan_name();
        String breakfast = suggest.getBreakfast();

        Map<String, String> response = new HashMap<>();

        if(breakfast == null){
            response.put("error_code", "404");
            response.put("error_message","식단 정보를 블러오는 것을 실패했습니다.");
            return ResponseEntity.status(404).body(response);
        }

        if(plan_name == "증량")
        {
            response.put("breakfast", String.valueOf(suggest.getBreakfast()));
            response.put("breakfast snack", String.valueOf(suggest.getSnack()));
            response.put("lunch", String.valueOf(suggest.getLunch()));
            response.put("lunch snack",String.valueOf(suggest.getSnack()));
            response.put("dinner",String.valueOf(suggest.getDinner()));
        } else {
            response.put("breakfast", String.valueOf(suggest.getBreakfast()));
            response.put("lunch", String.valueOf(suggest.getLunch()));
            response.put("snack",String.valueOf(suggest.getSnack()));
            response.put("dinner",String.valueOf(suggest.getDinner()));
        }

        return  new ResponseEntity<>(response,HttpStatus.OK);
    }
}

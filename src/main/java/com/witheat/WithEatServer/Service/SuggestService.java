package com.witheat.WithEatServer.Service;

import com.witheat.WithEatServer.Domain.entity.Height;
import com.witheat.WithEatServer.Domain.entity.Member;
import com.witheat.WithEatServer.Domain.entity.Suggest;
import com.witheat.WithEatServer.Domain.entity.Weight;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Repository.MemberRepository;
import com.witheat.WithEatServer.Repository.SuggestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

@Service
@Transactional
@RequiredArgsConstructor
public class SuggestService {
    private final MemberRepository memberRepository;
    private final SuggestRepository suggestRepository;
    private final MemberService memberService;

    //사용자의 selecet 보기
    public Long createSuggestId(Long memberId, String plan_name)
    {
        //1.User id get 해오기 -> 이러기 위해 userId를 인자로 받아야함
        Member member = memberRepository.findById(memberId).orElseThrow(()
        -> new BaseException(404,"유효하지 않은 ID"));

        //엔티티 생성 이후, suggestId 생성 및 사용자의 plan_name 저장
        //Suggest 엔티티를 메모리에 생성
        Suggest suggest = Suggest.builder()
                .plan_name(plan_name)
                .build();

        suggest = suggestRepository.save(suggest);

        //해당 엔티티의 planName 필드에 값을 설정
        return suggest.getSuggest_id();
    }

    //사람의 필요한 열량 구하는 메서드
    public Long calKcal(Long memberId)
    {
        //최신 정보를 받아오는 메서드
        Member member = memberRepository.findById(memberId).orElseThrow(()
        -> new BaseException(404, "유효하지 않은 유저 ID"));

        //최종 키와 몸무게 받아오기
        Height latestHeight = memberService.getLatestHeight(member);
        Weight latestWeight = memberService.getLatestWeight(member);

        //사용자의 필요한 열량 구하기
        Long memberKcal;
        //표준체중 구하기
        Long standardWeight = latestHeight.getHeight() * latestHeight.getHeight();

        //남자
        if(member.getGender() == true)
        {
            standardWeight = standardWeight * 22;
        } else if (member.getGender() == false) {
            standardWeight = standardWeight * 21;
        }else{
            //예외 처리 하기
            // BaseException("")
        }

        //비만도 체크하기
        String Obesity;
        Long ObesityLevel;

        ObesityLevel = latestWeight.getWeight() / standardWeight;
        ObesityLevel = ObesityLevel * 100;

        if(ObesityLevel < 90){
            Obesity = "underbody";
        } else if (ObesityLevel > 120){
            Obesity = "overbody";
        }else{
            Obesity = "normal";
        }

        if(Obesity == "underbody"){
            memberKcal = standardWeight * 35;
        } else if (Obesity == "normal") {
            memberKcal = standardWeight * 30;
        } else{
            memberKcal = standardWeight * 25;
        }

        return memberKcal;
    }

    public void calNutuient(Long memberKcal){

    }

}

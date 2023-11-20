package com.witheat.WithEatServer.Service;

import com.witheat.WithEatServer.Domain.entity.Member;
import com.witheat.WithEatServer.Domain.entity.Suggest;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Repository.MemberRepository;
import com.witheat.WithEatServer.Repository.SuggestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SuggestService {
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final SuggestRepository suggestRepository;

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


}

package com.witheat.WithEatServer.Service;

import com.witheat.WithEatServer.Config.JWT.JwtTokenProvideImpl;
import com.witheat.WithEatServer.Domain.Dto.request.AchieveStatusRequestDto;
import com.witheat.WithEatServer.Domain.Dto.request.MemberHeightRequestDto;
import com.witheat.WithEatServer.Domain.Dto.request.MemberWeightRequestDto;
import com.witheat.WithEatServer.Domain.Dto.response.*;
import com.witheat.WithEatServer.Domain.entity.*;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Repository.*;
import com.witheat.WithEatServer.WithEatServerApplication;
import com.witheat.WithEatServer.common.BaseErrorResponse;
import com.witheat.WithEatServer.common.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvideImpl jwtTokenProvideImpl;
    private final MemberHeightRepository memberHeightRepository;
    private final MemberWeightRepository memberWeightRepository;
    private final HeightRepository heightRepository;
    private final WeightRepository weightRepository;

//    @Override
    @Transactional
    public ResponseEntity logout(HttpServletRequest request) {
        String accessToken = jwtTokenProvideImpl.resolveToken(request);

        try {
            jwtTokenProvideImpl.logoutToken(accessToken);
        } catch (BaseException baseException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new BaseErrorResponse(HttpStatus.FORBIDDEN.value(), baseException.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse(HttpStatus.OK.value(), "로그아웃 되었습니다"));
    }

    // 사용자 일정 확인 (월별)
//    public CalendarResponseDto confirmCalendar(Long memberId) {
//        Member member = memberRepository.findById(memberId).orElseThrow(()
//                -> new BaseException(404, "유효하지 않은 멤버 ID"));
//
//        // 여기 getCalendars()로 바꿔야하나
//        List<CalendarResponseDto> result = new CalendarResponseDto(calendar.getCalender_id(), calendar.getCalendar_name(),
//                        calendar.getCalendar_date());
//
//        return result;
//    }

    // 사용자 정보(키) 받기
    public MemberHeightResponseDto receiveMemberHgtInform(Long memberId, MemberHeightRequestDto memberHeightRequestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(()
                ->new BaseException(HttpStatus.NO_CONTENT.value(), "Member not found"));

        Height height = Height.builder()
                .height(memberHeightRequestDto.getHeight())  // 키 설정
                .height_date(memberHeightRequestDto.getHeight_date())  // 기록 날짜 설정
                .build();

        heightRepository.save(height);

        MemberHeight memberHeight = new MemberHeight();
        memberHeight.setMember(member);
        memberHeight.setHeight(height);
        memberHeightRepository.save(memberHeight);

        return MemberHeightResponseDto.builder()
                .height_id(height.getHeight_id())
                .build();

    }

    // 사용자 정보(몸무게) 받기
    public MemberWeightResponseDto receiveMemberWgtInform(Long memberId, MemberWeightRequestDto memberWeightRequestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(()
                ->new BaseException(HttpStatus.NO_CONTENT.value(), "Member not found"));

        Weight weight = Weight.builder()
                .weight(memberWeightRequestDto.getWeight())  // 몸무게 설정
                .weight_date(memberWeightRequestDto.getWeight_date())   // 기록 날짜 설정
                .build();

       weightRepository.save(weight);

        MemberWeight memberWeight = new MemberWeight();
        memberWeight.setMember(member);
        memberWeight.setWeight(weight);
        memberWeightRepository.save(memberWeight);

        return MemberWeightResponseDto.builder()
                .weight_id(weight.getWeight_id())
                .build();

    }

    //사용자 체중 업데이트
    public void memberWeightResponseDto(Long memberId, MemberWeightRequestDto memberWeightRequestDto){
        //사용자 식별을 위해 Id 사용하여 사용자 찾기
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new BaseException(404, "유효하지 않은 유저 ID"));
        int newWeight = memberWeightRequestDto.getWeight();

        //최신 몸무게를 저장
        //새로운 Weight 엔티티 생성(해당 엔티티에 몸무게 정보가 있기 때문)
        Weight weight = new Weight();
        weight.setWeight(newWeight);
        weight.setWeight_date(LocalDate.now());

        //Member와 Weight를 연결해줄 memberWeight를 생성 및 초기화
        MemberWeight memberWeight = new MemberWeight();
        memberWeight.setMember(member);
        memberWeight.setWeight(weight);

        //MemberRepository에 저장하면서 자동으로 weight랑 member에 저장되게 끔 함
        //이는 일대다 관계를 다 엮어놔서 가능
        memberWeightRepository.save(memberWeight);
    }

    public List<MemberWeightResponseDto> getWeight(Long memberId, Long weightId){
        //오류 찾기
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new BaseException(404, "Member not found"));

        Weight weight = weightRepository.findById(weightId).orElseThrow(()
                -> new BaseException(404, "Weight not found"));

        List<MemberWeight> memberWeights = memberWeightRepository.findByMemberAndWeight(member,weight);

        List<MemberWeightResponseDto> weights = memberWeights.stream()
                .map(memberWeight -> new MemberWeightResponseDto(memberWeight.getMember().getMember_id(), memberWeight.getMember_weight_id()))
                .collect(Collectors.toList());

        return weights;

    }

    // 성취도 통계
    public AchieveCountResponseDto countAchieve(Long memberId,
                                                AchieveStatusRequestDto achieveStatusRequestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new BaseException(404, "Member not found"));

        if("success".equals(achieveStatusRequestDto.getStatus())) {
            member.setAchieve_success(member.getAchieve_success() + 1);
        } else if ("fail".equals(achieveStatusRequestDto.getStatus())) {
            member.setAchieve_fail(member.getAchieve_fail() + 1);
        }
        memberRepository.save(member);

        return AchieveCountResponseDto.builder()
                .achieve_success(member.getAchieve_success())
                .achieve_fail(member.getAchieve_fail())
                .build();
    }

    //최신 정보를 불러오기 메서드(suggestService에서 사용자 칼로리 계산하는데 사용)
    public Height getLatestHeight (Member member)
    {
        //리스트로 되어있는 member의 키의 값을 다 가져온 후
        List<MemberHeight> memberHeights = member.getMemberHeights();

        if(memberHeights.isEmpty()){
            return null;
        }

        //최신 날짜순으로 정렬
        Collections.sort(memberHeights);

        //첫 번째 요소가 최신 정보
        return memberHeights.get(0).getHeight();
    }

    public Weight getLatestWeight (Member member)
    {
        //리스트로 되어있는 member의 키의 값을 다 가져온 후
        List<MemberWeight> memberWeights = member.getMemberWeights();

        if(memberWeights.isEmpty()){
            return null;
        }

        //최신 날짜순으로 정렬
        Collections.sort(memberWeights);

        //첫 번째 요소가 최신 정보
        return memberWeights.get(0).getWeight();
    }
}
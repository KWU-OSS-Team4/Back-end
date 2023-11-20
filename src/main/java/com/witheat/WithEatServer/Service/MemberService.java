package com.witheat.WithEatServer.Service;

import com.witheat.WithEatServer.Config.JWT.JwtTokenProvideImpl;
import com.witheat.WithEatServer.Domain.Dto.request.MemberHeightRequestDto;
import com.witheat.WithEatServer.Domain.Dto.request.MemberWeightRequestDto;
import com.witheat.WithEatServer.Domain.Dto.response.*;
import com.witheat.WithEatServer.Domain.entity.*;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Repository.*;
import com.witheat.WithEatServer.common.BaseErrorResponse;
import com.witheat.WithEatServer.common.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


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
    private final CalendarRepository calendarRepository;

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
}
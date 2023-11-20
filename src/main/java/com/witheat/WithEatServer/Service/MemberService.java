package com.witheat.WithEatServer.Service;

import com.witheat.WithEatServer.Config.JWT.JwtTokenProvideImpl;
import com.witheat.WithEatServer.Domain.Dto.request.MemberHeightRequestDto;
import com.witheat.WithEatServer.Domain.Dto.request.MemberWeightRequestDto;
import com.witheat.WithEatServer.Domain.Dto.request.ProgressRequestDto;
import com.witheat.WithEatServer.Domain.Dto.response.CalendarResponseDto;
import com.witheat.WithEatServer.Domain.Dto.response.MemberHeightResponseDto;
import com.witheat.WithEatServer.Domain.Dto.response.MemberWeightResponseDto;
import com.witheat.WithEatServer.Domain.Dto.response.ProgressResponseDto;
import com.witheat.WithEatServer.Domain.entity.*;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Repository.*;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final JwtTokenProvideImpl jwtTokenProvideImpl;
    @Autowired
    private final MemberHeightRepository memberHeightRepository;
    @Autowired
    private final MemberWeightRepository memberWeightRepository;
    @Autowired
    private final HeightRepository heightRepository;
    @Autowired
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


    public List<CalendarResponseDto> confirmCalendar(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new BaseException(404, "유효하지 않은 멤버 ID"));

        // 여기 getCalendars()로 바꿔야하나
        List<CalendarResponseDto> result = member.getCalendars().stream()
                .map(calendar -> new CalendarResponseDto(calendar.getCalender_id(), calendar.getCalendar_name(),
                        calendar.getCalendar_date())).collect(Collectors.toList());

        return result;
    }

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

    // 사용자 진행도 받기
    public ProgressResponseDto receiveMemberProgress(Long memberId, ProgressRequestDto progressRequestDto) {
        // 요청으로부터 사용자 진행도 가져오기
        int progress_per = progressRequestDto.getProgress_per();

        Member member = memberRepository.findById(memberId).orElseThrow(()
                ->new BaseException(HttpStatus.NO_CONTENT.value(), "Member not found"));

        Progress progress = Progress.builder()
                .progress_per(progressRequestDto.getProgress_per())   // 진행도 설정
                .progress_date(progressRequestDto.getProgress_date())   // 기록 날짜 설정
                .member(member)  // 진행도와 멤버 연결
                .build();

        memberRepository.save(member);

        ProgressResponseDto progressResponseDto = new ProgressResponseDto(progress.getProgress_id());
        return progressResponseDto;

    }

    //사용자 체중 업데이트
    public MemberWeightResponseDto memberWeightResponseDto(Long memberId, MemberWeightRequestDto memberWeightRequestDto){
        //사용자 식별을 위해 Id 사용하여 사용자 찾기
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new BaseException(404, "유효하지 않은 유저 ID"));
        int newWeight = memberWeightRequestDto.getWeight();

        //최신 몸무게 받아와야함
        Weight latestWeight = member.getWeights().stream()
                .max(Comparator.comparing(Weight::getWeight_date))
                .orElse(null);

        //새로운 정보 저장
        Weight newWeightEntry = Weight.builder()
                .weight(newWeight)
                .weight_date(LocalDate.now())
                .build();

        //최신 몸무게 정보를 업데이트
        if(latestWeight != null){
            latestWeight.setWeight(newWeight);
            latestWeight.setWeight_date(LocalDate.now());
        }else{
            //최신 몸무게 정보가 없으면 새로운 몸무게 정보를 생성
            member.getWeights().add(newWeightEntry);
            newWeightEntry.setMember(member);
            weightRepository.save(newWeightEntry);
        }

        memberRepository.save(member);
        //weightRepository.save(latestWeight);

        MemberWeightResponseDto memberWeightResponseDto = new MemberWeightResponseDto(member.getMember_id(), newWeightEntry.getWeight_id());
        return memberWeightResponseDto;
    }

    public List<MemberWeightResponseDto> getWeight(Long memberId, Long weightId){
        //오류 찾기
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new BaseException(404, "유효하지 않은 유저 ID"));

        Weight weight = weightRepository.findById(weightId).orElseThrow(()
                -> new BaseException(404, "유효하지 않은 weight ID"));

        List<MemberWeight> memberWeights = memberWeightRepository.findByMemberAndWeight(member,weight);

        List<MemberWeightResponseDto> weights = memberWeights.stream()
                .map(memberWeight -> new MemberWeightResponseDto(memberWeight.getMember().getMember_id(), memberWeight.getMember_weight_id()))
                .collect(Collectors.toList());

        return weights;

    }
}
package com.witheat.WithEatServer.Service;

import com.witheat.WithEatServer.Config.JWT.JwtTokenProvideImpl;
import com.witheat.WithEatServer.Domain.Dto.response.CalendarResponseDto;
import com.witheat.WithEatServer.Domain.entity.Member;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Repository.MemberRepository;
import com.witheat.WithEatServer.common.BaseErrorResponse;
import com.witheat.WithEatServer.common.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvideImpl jwtTokenProvideImpl;


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



    public List<CalendarResponseDto> confirmCalendar(Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow(()
                -> new BaseException(404, "유효하지 않은 유저 ID"));

        // 여기 getCalendars()로 바꿔야하나
        List<CalendarResponseDto> result = member.getCalendars().stream()
                .map(calendar -> new CalendarResponseDto(calendar.getCalender_id(), calendar.getCalendar_name(),
                        calendar.getCalendar_date())).collect(Collectors.toList());

        return result;
    }

}
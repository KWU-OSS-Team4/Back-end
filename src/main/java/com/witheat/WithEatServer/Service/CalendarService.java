package com.witheat.WithEatServer.Service;

import com.witheat.WithEatServer.Domain.Dto.request.CalendarCreateRequestDto;
import com.witheat.WithEatServer.Domain.Dto.response.CalendarCreateResponseDto;
import com.witheat.WithEatServer.Domain.entity.Calendar;
import com.witheat.WithEatServer.Domain.entity.Member;
import com.witheat.WithEatServer.Domain.entity.MemberCalendar;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Repository.CalendarRepository;
import com.witheat.WithEatServer.Repository.MemberCalendarRepository;
import com.witheat.WithEatServer.Repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final MemberRepository memberRepository;
    private final MemberCalendarRepository memberCalendarRepository;
    public CalendarService(CalendarRepository calendarRepository, MemberRepository memberRepository, MemberCalendarRepository memberCalendarRepository){
        this.calendarRepository = calendarRepository;
        this.memberRepository = memberRepository;
        this.memberCalendarRepository = memberCalendarRepository;
    }
    public CalendarCreateResponseDto generateCalendar(Long memberId,
                                                      CalendarCreateRequestDto calendarCreateRequestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(()
                ->new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        Calendar calendar = Calendar.builder()
                .calendar_name(calendarCreateRequestDto.getCalendar_name())  // 목표 이름 설정
                .calendar_date(calendarCreateRequestDto.getCalendar_date())  // 날짜 설정
                .build();

        calendarRepository.save(calendar);

        MemberCalendar memberCalendar = new MemberCalendar();
        memberCalendar.setMember(member);
        memberCalendar.setCalendar(calendar);
        memberCalendarRepository.save(memberCalendar);

        return CalendarCreateResponseDto.builder()
                .calendarId(calendar.getCalender_id())
                .build();
    }

    @Transactional
    public void deleteCalendar(Long memberId, Long calendarId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));
        Calendar calendar = calendarRepository.findById(calendarId).orElseThrow(()
                -> new BaseException(HttpStatus.NOT_FOUND.value(), "Calendar not found"));

        calendarRepository.deleteById(calendarId);
    }
}

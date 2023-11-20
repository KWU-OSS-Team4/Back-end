package com.witheat.WithEatServer.Service;

import com.witheat.WithEatServer.Domain.Dto.request.CalendarCreateRequestDto;
import com.witheat.WithEatServer.Domain.Dto.request.FilteringScheduleRequestDto;
import com.witheat.WithEatServer.Domain.Dto.request.MonthlyRequestDto;
import com.witheat.WithEatServer.Domain.Dto.response.CalendarCreateResponseDto;
import com.witheat.WithEatServer.Domain.Dto.response.FilterCalendars;
import com.witheat.WithEatServer.Domain.Dto.response.MonthlyResponseDto;
import com.witheat.WithEatServer.Domain.entity.Calendar;
import com.witheat.WithEatServer.Domain.entity.Member;
import com.witheat.WithEatServer.Domain.entity.MemberCalendar;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Repository.CalendarRepository;
import com.witheat.WithEatServer.Repository.MemberCalendarRepository;
import com.witheat.WithEatServer.Repository.MemberRepository;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    // 사용자 일정 생성
    public CalendarCreateResponseDto generateCalendar(Long memberId,
                                                      CalendarCreateRequestDto calendarCreateRequestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(()
                ->new BaseException(HttpStatus.NOT_FOUND.value(), "Member not found"));

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

    // 월별 날짜 리스트 조회
    public List<MonthlyResponseDto> getDateList(Long memberId, MonthlyRequestDto monthlyRequestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new BaseException(HttpStatus.NOT_FOUND.value(), "Member not found"));

        Set<Calendar> dateRequestMonth = member.getMemberCalendars().stream()
                .map(MemberCalendar::getCalendar)
                .filter(date ->  isDateInRequestMonth(date.getCalendar_date(), monthlyRequestDto.getDate_request()))  // 여기 왜 에러나시죠???
                .collect(Collectors.toSet());   // 중복 제거위해 set으로 수집

        List<MonthlyResponseDto> monthlyResponseDtos = dateRequestMonth.stream()
                .sorted()
                .map(date -> MonthlyResponseDto.builder().date_list(date.getCalendar_date()).build())
                .collect(Collectors.toList());;

        return monthlyResponseDtos;
    }

    private boolean isDateInRequestMonth(LocalDate dateCheck, LocalDate requestMonth) {
        return dateCheck.getMonthValue() == requestMonth.getMonthValue()
                && dateCheck.getYear() == requestMonth.getYear();
    }

    // 날짜별 스케줄
    public List<FilterCalendars> findCalendars(Long memberId, FilteringScheduleRequestDto filteringScheduleRequestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new BaseException(HttpStatus.NOT_MODIFIED.value(), "Member not found"));
        LocalDate targetDate = filteringScheduleRequestDto.getCalendar_date();

        List<Calendar> calendars = member.getMemberCalendars().stream()
                .map(MemberCalendar::getCalendar)
                .filter(calendar -> isDateRange(targetDate, calendar.getCalendar_date()))
                .toList();    // .collect(Collectors.toList());  -- 이게 맞는거 아닐까...????

        return calendars.stream()
                .map(this::mapFilterCalendars)
                .collect(Collectors.toList());
    }

    private boolean isDateRange(LocalDate targetDate, LocalDate calendarDate) {
        return !targetDate.isEqual(calendarDate);
    }

    private FilterCalendars mapFilterCalendars(Calendar calendar) {
        return FilterCalendars.builder()
                .calendar_name(calendar.getCalendar_name())
                .calendar_date(calendar.getCalendar_date())
                .build();
    }


    // 사용자 일정 삭제
    @Transactional
    public void deleteCalendar(Long memberId, Long calendarId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new BaseException(HttpStatus.NOT_FOUND.value(), "Member not found"));
        Calendar calendar = calendarRepository.findById(calendarId).orElseThrow(()
                -> new BaseException(HttpStatus.NOT_FOUND.value(), "Calendar not found"));

        calendarRepository.deleteById(calendarId);
    }
}

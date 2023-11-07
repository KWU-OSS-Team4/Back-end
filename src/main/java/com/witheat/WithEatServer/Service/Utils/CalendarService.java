package com.witheat.WithEatServer.Service.Utils;

import com.witheat.WithEatServer.Domain.Dto.request.CalendarCreateRequestDto;
import com.witheat.WithEatServer.Domain.Dto.response.CalendarCreateResponseDto;
import com.witheat.WithEatServer.Domain.entity.Calendar;
import com.witheat.WithEatServer.Domain.entity.User;
import com.witheat.WithEatServer.Domain.entity.UserCalendar;
import com.witheat.WithEatServer.Domain.entity.Weight;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Repository.CalendarRepository;
import com.witheat.WithEatServer.Repository.UserCalendarRepository;
import com.witheat.WithEatServer.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;
    private final UserCalendarRepository userCalendarRepository;

    public CalendarCreateResponseDto generateCalendar(Long userId,
                                                      CalendarCreateRequestDto calendarCreateRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(()
                ->new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        Calendar calendar = Calendar.builder()
                .calendar_name(calendarCreateRequestDto.getCalendar_name())  // 목표 이름 설정
                .calendar_date(calendarCreateRequestDto.getCalendar_date())  // 날짜 설정
                .build();

        calendarRepository.save(calendar);

        UserCalendar userCalendar = new UserCalendar();
        userCalendar.setUser(user);
        userCalendar.setCalendar(calendar);
        userCalendarRepository.save(userCalendar);

        return CalendarCreateResponseDto.builder()
                .calendarId(calendar.getCalender_id())
                .build();
    }
}

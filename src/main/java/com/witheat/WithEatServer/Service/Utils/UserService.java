package com.witheat.WithEatServer.Service.Utils;

import com.witheat.WithEatServer.Domain.Dto.response.CalendarResponseDto;
import com.witheat.WithEatServer.Domain.entity.User;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Repository.CalendarRepository;
import com.witheat.WithEatServer.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<CalendarResponseDto> confirmCalendar(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new BaseException(404, "유효하지 않은 유저 ID"));

        // 여기 getCalendars()로 바꿔야하나
        List<CalendarResponseDto> result = user.getCalendars().stream()
                .map(calendar -> new CalendarResponseDto(calendar.getCalender_id(), calendar.getCalendar_name(),
                        calendar.getCalendar_date())).collect(Collectors.toList());

        return result;
    }

}

package com.witheat.WithEatServer.Service;

import com.witheat.WithEatServer.Domain.Dto.request.UserWeightCreateRequestDto;
import com.witheat.WithEatServer.Domain.Dto.response.CalendarResponseDto;
import com.witheat.WithEatServer.Domain.Dto.response.UserWeightCreateResponseDto;
import com.witheat.WithEatServer.Domain.entity.User;
import com.witheat.WithEatServer.Domain.entity.UserWeight;
import com.witheat.WithEatServer.Domain.entity.Weight;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Repository.CalendarRepository;
import com.witheat.WithEatServer.Repository.UserRepository;
import com.witheat.WithEatServer.Repository.WeightRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    private final WeightRepository weightRepository;
    public UserWeightCreateResponseDto userWeightCreateResponseDto(Long userId, UserWeightCreateRequestDto userWeightCreateRequestDto){
        //사용자 식별을 위해 Id 사용하여 사용자 찾기
        User user = userRepository.findById(userId).orElseThrow(()
                -> new BaseException(404, "유효하지 않은 유저 ID"));
        int newWeight = userWeightCreateRequestDto.getWeight();

        //최신 몸무게 받아와야함
        Weight latestWeight = user.getWeights().stream()
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
            user.getWeights().add(newWeightEntry);
            newWeightEntry.setUser(user);
            weightRepository.save(newWeightEntry);
        }

        userRepository.save(user);

        return new UserWeightCreateResponseDto(newWeightEntry.getWeight_id());
    }
}

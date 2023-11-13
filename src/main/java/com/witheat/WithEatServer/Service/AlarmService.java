package com.witheat.WithEatServer.Service;

import com.witheat.WithEatServer.Domain.Dto.request.AlarmCreateRequestDto;
import com.witheat.WithEatServer.Domain.Dto.response.AlarmCreateResponseDto;
import com.witheat.WithEatServer.Repository.AlarmRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor

public class AlarmService {
    private final AlarmRepository alarmRepository;

    public AlarmCreateResponseDto generateAlarm(Long userId, AlarmCreateRequestDto alarmCreateRequestDto){
        //알람을 생성하고 추가하는 것을 구현해야함
        /*
        로직
        1. 알람 목록 page에서 + 버튼을 누르면 알람 생성 page로 이동함
        2. 사용자로부터 알람 이름과 타이머를 입력  받음
        3. 입력받은 정보로 알람 생성
        4. 알람 생성되면 DTO 로 알람 생성 완료라고 하고 알람 목록 page로 다시 돌아감
        */
    }

}
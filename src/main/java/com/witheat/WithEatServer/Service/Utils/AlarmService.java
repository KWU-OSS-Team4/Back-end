package com.witheat.WithEatServer.Service.Utils;

import com.witheat.WithEatServer.Repository.AlarmRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor

public class AlarmService {
    private final AlarmRepository alarmRepository;

}
package com.witheat.WithEatServer.Service.Utils;

import com.witheat.WithEatServer.Domain.Dto.request.UserRequestDto;
import com.witheat.WithEatServer.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

//    @Transactional
//    @Override
//    public void join(UserRequestDto userRequestDto) {
//
//    }
}

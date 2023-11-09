package com.witheat.WithEatServer.Service.Utils;

import com.witheat.WithEatServer.Domain.Dto.request.UserRequestDto;
import com.witheat.WithEatServer.Domain.entity.User;
import com.witheat.WithEatServer.Exception.BadRequestException;
import com.witheat.WithEatServer.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    @Transactional
//    @Override   // 이거 필요한건가....???
    public void join(UserRequestDto userRequestDto) {
        validateUserRequest(userRequestDto);
        validateDuplicateEmail(userRequestDto.getEmail());
        userRepository.save(new User(userRequestDto));  // 뭐야 여기 왜이래
    }


    private void validateUserRequest(UserRequestDto userRequestDto) {
        if(userRequestDto.getName() == null) throw new BadRequestException("이름을 입력하세요");
        if(userRequestDto.getEmail() == null) throw new BadRequestException("이메일을 입력하세요");
        if(userRequestDto.getPassword() == null) throw new BadRequestException("비밀번호를 입력하세요");
        if(userRequestDto.getGender() == null) throw new BadRequestException("성별을 선택하세요");
        if(userRequestDto.getPlan_name() == null) throw new BadRequestException("목표를 선택하세요");
        // 여기에 키랑 몸무게 입력하라고 해야할 것 같은데 어떻게 해야하지...????????
    }

    private void validateDuplicateEmail(String email) {
        if(userRepository.findByEmail(email).stream().toList().size()>0) {
            throw new BadRequestException("이미 회원가입된 이메일입니다.");
        }
    }
}

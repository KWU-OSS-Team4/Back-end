package com.witheat.WithEatServer.Service;

import com.witheat.WithEatServer.Domain.Dto.response.SuggestTypeCreateResponseDto;
import com.witheat.WithEatServer.Domain.Dto.response.UserWeightCreateResponseDto;
import com.witheat.WithEatServer.Domain.entity.User;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Repository.SuggestRepository;
import com.witheat.WithEatServer.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class SuggestService {
    private final UserRepository userRepository;
    private final SuggestRepository suggestRepository;

    //사용자의 selecet 보기
    public void planNameSelection(Long userId)
    {
        //1.User id get 해오기 -> 이러기 위해 userId를 인자로 받아야함
        User user = userRepository.findById(userId).orElseThrow(()
        -> new BaseException(404,"유효하지 않은 ID"));

        //사용자가 선택한 로직이 무엇인지

    }


}

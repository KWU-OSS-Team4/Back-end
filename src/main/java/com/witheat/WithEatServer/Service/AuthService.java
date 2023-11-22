package com.witheat.WithEatServer.Service;

import com.witheat.WithEatServer.Config.JWT.JwtTokenProvideImpl;
import com.witheat.WithEatServer.Domain.Dto.request.MemberRequestDto;
import com.witheat.WithEatServer.Domain.Dto.response.LoginResponse;
import com.witheat.WithEatServer.Domain.entity.Member;
import com.witheat.WithEatServer.Exception.BadRequestException;
import com.witheat.WithEatServer.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;

    // JWT 이용
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvideImpl jwtTokenProvideImpl;

    // 회원가입
    @Transactional
//    @Override   // 이거 필요한건가....???
    public void join(MemberRequestDto memberRequestDto) {
        validateMemberRequest(memberRequestDto);
        validateDuplicateEmail(memberRequestDto.getEmail());
        memberRepository.save(new Member(memberRequestDto));  // 뭐야 여기 왜이래
    }

    @Transactional(readOnly = true)
//    @Override
    public LoginResponse login(String email, String password) {
        Member member = getMember(email);
        member.validatePassword(password);
        return new LoginResponse(member.getName(),
                jwtTokenProvideImpl.generateToken(member));
    }

    private void validateMemberRequest(MemberRequestDto memberRequestDto) {
        if(memberRequestDto.getName() == null) throw new BadRequestException("이름을 입력하세요");
        if(memberRequestDto.getEmail() == null) throw new BadRequestException("이메일을 입력하세요");
        if(memberRequestDto.getPassword() == null) throw new BadRequestException("비밀번호를 입력하세요");
        if(memberRequestDto.getGender() == null) throw new BadRequestException("성별을 선택하세요");
        if(memberRequestDto.getPlan_name() == null) throw new BadRequestException("목표를 선택하세요");
    }

    private void validateDuplicateEmail(String email) {
        if(memberRepository.findByEmail(email).stream().toList().size()>0) {
            throw new BadRequestException("이미 회원가입된 이메일입니다.");
        }
    }

    private Member getMember(String email) {
        return memberRepository.findByEmail(email).stream()
                .findFirst()
                .orElseThrow(()->new BadRequestException("회원가입되지 않은 이메일"));
    }
}

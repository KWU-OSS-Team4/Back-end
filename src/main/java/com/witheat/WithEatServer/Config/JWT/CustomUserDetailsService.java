package com.witheat.WithEatServer.Config.JWT;

import com.witheat.WithEatServer.Domain.entity.Member;
import com.witheat.WithEatServer.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email).stream()
                .map(this::createUserDetails).findFirst()
                .orElseThrow(()->new UsernameNotFoundException("유저를 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(Member member) {
        return User.builder()
                .username(member.getEmail())  // 이거 getEmail 해야하는데 왜안됨?
                .password(passwordEncoder.encode(member.getPassword()))
                .build();
    }
}

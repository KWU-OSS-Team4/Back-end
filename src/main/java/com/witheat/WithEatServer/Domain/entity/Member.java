package com.witheat.WithEatServer.Domain.entity;

import com.witheat.WithEatServer.Domain.Dto.request.MemberRequestDto;
import com.witheat.WithEatServer.Exception.BadRequestException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Member extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean agreement;

    @Column(nullable = false)
    private Boolean gender;

    @Column(nullable = false)
    private String plan_name;

    // height, weight는 OneToMany
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    public List<UserHeight>

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Height> heights = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Weight> weights = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<MemberCalendar> memberCalendars = new ArrayList<>();

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Calendar> calendars = new ArrayList<>();

    @Builder
    public Member(String name, String email, String password, boolean agreement, boolean gender, String plan_name) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.agreement = agreement;
        this.gender = gender;
        this.plan_name = plan_name;
    }

    public Member(MemberRequestDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.gender = dto.getGender();
        this.plan_name = dto.getPlan_name();
    }

    public boolean isPasswordMatched(String password) { return this.password.equals(password); }
    public long getMemberId() {
        return member_id;
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

    public void validatePassword(String password) {
        if(!isPasswordMatched(password)) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }
    }
}
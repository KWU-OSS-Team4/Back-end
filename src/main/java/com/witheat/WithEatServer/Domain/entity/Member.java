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
import java.util.Set;

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

    @Column(nullable = false)
    private int requiredCalories;

    // height, weight는 OneToMany
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    public List<UserHeight>

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<MemberHeight> memberHeights = new ArrayList<>();    // 바뀜..?

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<MemberWeight> memberWeights = new ArrayList<>();    // 바뀜...??

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<MemberCalendar> memberCalendars = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Calendar> calendars;   // public List<Calendar> calendars = new ArrayList<>();

    //mappedBy : 연관관계의 주인
    //cascade ~ : 부모 엔티티(Member)의 변경이 자식엔티티에 영향을 미치돌고 함
    //orph ~ : 부모 엔티티에서 제거된 자식 엔티티도 DB에서 삭제되도록 함
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Suggest> suggests = new ArrayList<>();

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

    //User과 Weight UserWeight 를 모두 양방향 관계를 가지도록 함
    /*public void addWeight(Weight weight){
        memberWeights.add(weight);
        weight.setMember(this);
    }*/

}
package com.witheat.WithEatServer.Domain.entity;

import com.witheat.WithEatServer.Domain.Dto.request.UserRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Height> heights = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Weight> weights = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<UserCalendar> userCalendars = new ArrayList<>();

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Calendar> calendars = new ArrayList<>();

    @Builder
    public User(String name, String email, String password, boolean agreement, boolean gender, String plan_name) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.agreement = agreement;
        this.gender = gender;
        this.plan_name = plan_name;
    }

    public User(UserRequestDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.gender = dto.getGender();
        this.plan_name = dto.getPlan_name();
    }

    //User과 Weight UserWeight 를 모두 양방향 관계를 가지도록 함
    public void addWeight(Weight weight){
        weights.add(weight);
        weight.setUser(this);
    }

}
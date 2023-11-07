package com.witheat.WithEatServer.Domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
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
    private boolean gender;

    @Column(nullable = false)
    private String plan_name;

    // height, weight는 OneToMany
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    public List<UserHeight>
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<UserCalendar> userCalendars = new ArrayList<>();

    @Builder
    public User(String name, String email, String password, boolean agreement, boolean gender, String plan_name) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.agreement = agreement;
        this.gender = gender;
        this.plan_name = plan_name;
    }
}

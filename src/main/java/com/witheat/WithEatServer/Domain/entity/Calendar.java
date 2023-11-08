package com.witheat.WithEatServer.Domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Calendar extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calender_id;

    @Column(nullable = false)  // nullable 안 넣어도 될 것 같기도 하고?
    private String calendar_name;

    @Column(nullable = false)
    private LocalDate calendar_date;

    @Column
    private String feedback;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<UserCalendar> userCalendars = new ArrayList<>();

    @Builder
    public Calendar(String calendar_name, LocalDate calendar_date, String feedback) {
        this.calendar_name = calendar_name;
        this.calendar_date = calendar_date;
        this.feedback = feedback;
    }
}

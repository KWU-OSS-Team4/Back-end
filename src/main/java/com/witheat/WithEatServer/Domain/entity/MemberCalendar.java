package com.witheat.WithEatServer.Domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class MemberCalendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_calendar_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}

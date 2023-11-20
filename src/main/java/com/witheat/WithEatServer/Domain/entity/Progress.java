package com.witheat.WithEatServer.Domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
@Entity
public class Progress extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long progress_id;

    @Column
    private int progress_per;

    @Column
    private LocalDate progress_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @Builder
    public Progress (int progress_per, LocalDate progress_date, Member member) {
        this.progress_per = progress_per;
        this.progress_date = progress_date;
        this.member = member;
    }
}

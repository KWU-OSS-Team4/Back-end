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
    private LocalDate progress_Date;

    // @OneToMany ...???
    @Builder
    public Progress (int progress_per, LocalDate progress_Date) {
        this.progress_per = progress_per;
        this.progress_Date = progress_Date;
    }
}

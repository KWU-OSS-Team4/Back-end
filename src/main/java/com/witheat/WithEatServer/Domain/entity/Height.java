package com.witheat.WithEatServer.Domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Height extends Time{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long height_id;

    @Column(nullable = false)
    private int height;

    @Column (nullable = false)
    private LocalDate height_date;

    // @OneToMany 필요

    @Builder
    public Height(Integer height, LocalDate height_date) {
        this.height = height;
        this.height_date = height_date;
    }
}

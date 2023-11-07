package com.witheat.WithEatServer.Domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class Weight extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weight_id;

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false)
    private LocalDate weight_date;

    // @OneToMany -> user랑 weight 엮어서 만들기

    @Builder
    public Weight(int weight, LocalDate weight_date) {
        this.weight = weight;
        this.weight_date = weight_date;
    }
}

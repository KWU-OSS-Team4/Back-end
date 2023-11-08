package com.witheat.WithEatServer.Domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@Entity
public class Suggest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long suggest_id;

    @Column(nullable = false)
    private String plan_name;

    @Column
    private String breakfast;   // 이걸 list로 받아야하려나

    @Column
    private String lunch;

    @Column
    private String dinner;

    @Builder
    public Suggest (String plan_name, String breakfast, String lunch, String dinner) {
        this.plan_name = plan_name;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }
}

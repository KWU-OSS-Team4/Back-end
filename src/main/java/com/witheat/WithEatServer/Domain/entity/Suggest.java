package com.witheat.WithEatServer.Domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Suggest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long suggest_id;

    @Column(nullable = false)
    private String plan_name; //유지 감량 등등

    //아침은 리스트나 class로 받는게 나아보임
    @Column
    private String breakfast;   // 이걸 list로 받아야하려나

    @Column
    private String lunch;

    @Column
    private String dinner;

    @Column
    private String snack;

    @Builder
    public Suggest(String plan_name)
    {
        this.plan_name = plan_name;
    }

    @Builder
    public Suggest (String plan_name, String breakfast, String lunch, String dinner,String snack) {
        this.plan_name = plan_name;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.snack = snack;
    }

}

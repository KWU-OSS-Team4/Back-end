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
    private String plan_name; //유지 감량 등

    @Column(nullable = false)
    private int carbohydrate;

    @Column(nullable = false)
    private int protein;

    @Column(nullable = false)
    private int fat;

    //아침은 리스트나 class로 받는게 나아보임
    @Column
    private int breakfast;   // 이걸 list로 받아야하려나

    @Column
    private int lunch;

    @Column
    private int dinner;

    @Column
    private int snack;

    //현재 엔티티와 다른 엔티티와의 다대일 관계를 맺을 예정
    //여러개의 추천 식단이 하나의 사용자에 속함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Suggest(String plan_name)
    {
        this.plan_name = plan_name;
    }

    @Builder
    public Suggest (String plan_name, int breakfast, int lunch, int dinner, int snack,Member member) {
        this.plan_name = plan_name;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.snack = snack;
        this.member = member;
    }

    //칼로리 계산을 위한 메서드
}

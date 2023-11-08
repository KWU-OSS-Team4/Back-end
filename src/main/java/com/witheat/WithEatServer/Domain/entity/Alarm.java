package com.witheat.WithEatServer.Domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Alarm extends Time{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarm_id;

    @Column
    private String intermit_plan;  // 근데 이걸 스트링으로 받는게 맞음?

    @Column
    private String water_plan;

    @Column
    private String empty_plan;

    // button..???
    /*@Column
    private String user_plan;*/

    @Builder
    public Alarm(String intermit_plan, String water_plan, String empty_plan) {
        this.intermit_plan = intermit_plan;
        this.water_plan = water_plan;
        this.empty_plan = empty_plan;
    }
}

package com.witheat.WithEatServer.Domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

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
    @Column
    private String user_alarm;

    @Column
    private LocalTime timer;

    @Column
    private LocalTime startTime;

    public void updateInterPlan(String newInterPlan){
        this.intermit_plan = newInterPlan;
    }

    public void updateWaterPlan(String newWaterPlan){
        this.water_plan = newWaterPlan;
    }

    public void updateEmptyPlan(String newEmptyPlan){
        this.empty_plan = newEmptyPlan;
    }

    public void updateTimer(LocalTime newTimer){
        this.timer = newTimer;
    }
    @Builder
    //사용자 알람 추가 기능을 위한 생성자
    public Alarm(){
        
    }

    /*
      public Alarm(String intermit_plan, String water_plan, String empty_plan) {
        this.intermit_plan = intermit_plan;
        this.water_plan = water_plan;
        this.empty_plan = empty_plan;
    */
}

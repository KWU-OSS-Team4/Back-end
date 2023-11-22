package com.witheat.WithEatServer.Domain.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
public class Weight extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weight_id;

    //@Getter
    @Column(nullable = false)
    private int weight;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate weight_date; //몸무게 마지막 변경 날짜

    //user가 weight 를 관리하게 하기
    @OneToMany(mappedBy = "weight", cascade = CascadeType.ALL, orphanRemoval = true) //-> user랑 weight 엮어서 만들기
    private final List<MemberWeight> memberWeights = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Weight(int weight, LocalDate weight_date) {
        this.weight = weight;
        this.weight_date = weight_date;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setWeight_date(LocalDate weight_date){
        this.weight_date = weight_date;
    }

    /*
    public void setMember(Member member)
    {
        this.member =member;
        member.getMemberWeights().add(this);
    }
     */
}

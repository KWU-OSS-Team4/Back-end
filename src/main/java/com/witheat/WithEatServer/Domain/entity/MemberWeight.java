package com.witheat.WithEatServer.Domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class MemberWeight implements Comparable<MemberWeight>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_weight_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weight_id")
    private Weight weight;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    //Comparable 인터펭스를 구현한 클래스에 사용되는 메서드
    //클래스의 객체간의 순서를 정의함 -> 객체 정렬 등의 역할에 용이
    //Collections.sort() 를 이용
    public int compareTo(MemberWeight other)
    {
        return this.weight.getWeight_date().compareTo(other.getWeight().getWeight_date());
    }
}
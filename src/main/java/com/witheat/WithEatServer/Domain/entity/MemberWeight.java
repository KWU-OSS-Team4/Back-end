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
public class MemberWeight {
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
}
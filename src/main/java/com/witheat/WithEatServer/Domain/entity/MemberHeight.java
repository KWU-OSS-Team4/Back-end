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
public class MemberHeight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_height_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "height_id")
    private Height height;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setHeight(Height height) {
        this.height = height;
    }
}

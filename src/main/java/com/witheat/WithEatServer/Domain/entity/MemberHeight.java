package com.witheat.WithEatServer.Domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MemberHeight implements Comparable<MemberHeight>{
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

    //Comparable 인터펭스를 구현한 클래스에 사용되는 메서드
    //클래스의 객체간의 순서를 정의함 -> 객체 정렬 등의 역할에 용이
    //Collections.sort() 를 이용
    public int compareTo(MemberHeight other)
    {
        return this.height.getHeight_date().compareTo(other.getHeight().getHeight_date());
    }
}

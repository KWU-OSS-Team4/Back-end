package com.witheat.WithEatServer.Domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Height extends Time{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long height_id;

    @Column(nullable = false)
    private int height;

    @Column (nullable = false)
    private LocalDate height_date;

    //Member과의 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //Height 관계
    @OneToMany(mappedBy = "height", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<MemberHeight> memberHeights = new ArrayList<>();

    @Builder
    public Height(Integer height, LocalDate height_date) {
        this.height = height;
        this.height_date = height_date;
    }
}

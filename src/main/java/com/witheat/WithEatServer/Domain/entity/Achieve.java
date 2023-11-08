package com.witheat.WithEatServer.Domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Achieve extends Time { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long achieve_id;
    
    @Column(nullable = false)
    private int achievement;
    
    @Column(nullable = false)
    private LocalDateTime create_at;
    
    // @OneToMany 필요할 듯 싶은데
    
    @Builder
    public Achieve(int achievement, LocalDateTime create_at) {
        this.achievement = achievement;
        this.create_at = create_at;
    }
}

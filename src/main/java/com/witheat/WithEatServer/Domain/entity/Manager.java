package com.witheat.WithEatServer.Domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diet_id;

    @Column
    private int carbon;

    @Column
    private int protein;

    @Column
    private int fat;

    @Column
    private String food;

    @Column
    private String amount;

    //@OneToMany???????
    @Builder
    public Manager(int carbon, int protein, int fat, String food, String amount) {
        this.carbon = carbon;
        this.protein = protein;
        this.fat = fat;
        this.food = food;
        this.amount = amount;
    }
}

package com.witheat.WithEatServer.Repository;

import com.witheat.WithEatServer.Domain.entity.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightRepository extends JpaRepository<Weight, Long> {
}

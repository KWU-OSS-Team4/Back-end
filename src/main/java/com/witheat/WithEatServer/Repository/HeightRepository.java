package com.witheat.WithEatServer.Repository;

import com.witheat.WithEatServer.Domain.entity.Height;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface HeightRepository extends JpaRepository<Height, Long> {
}

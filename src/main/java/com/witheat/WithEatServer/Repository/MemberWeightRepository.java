package com.witheat.WithEatServer.Repository;

import com.witheat.WithEatServer.Domain.entity.MemberWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface MemberWeightRepository extends JpaRepository<MemberWeight, Long> {
}

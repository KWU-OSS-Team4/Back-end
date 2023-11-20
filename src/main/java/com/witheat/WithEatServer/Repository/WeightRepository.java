package com.witheat.WithEatServer.Repository;

import com.witheat.WithEatServer.Domain.entity.Member;
import com.witheat.WithEatServer.Domain.entity.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeightRepository extends JpaRepository<Weight, Long> {
    //칼로리 계산하기 위해 최신 정보 불러오기
    //Optional<Weight> findTopByMemberOrderByWeight_dateDesc(Member member);
}

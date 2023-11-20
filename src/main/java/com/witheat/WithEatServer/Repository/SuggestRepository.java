package com.witheat.WithEatServer.Repository;

import com.witheat.WithEatServer.Domain.entity.Suggest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestRepository extends JpaRepository<Suggest,Long> {
    //필요한 쿼리 메서드 추가
}

package com.witheat.WithEatServer.Repository;

import com.witheat.WithEatServer.Domain.entity.Suggest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestRepository extends JpaRepository<Suggest,Long> {
    //List<Suggest> findByMemberId(Long member_Id);
}

package com.witheat.WithEatServer.Repository;

import com.witheat.WithEatServer.Domain.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

}

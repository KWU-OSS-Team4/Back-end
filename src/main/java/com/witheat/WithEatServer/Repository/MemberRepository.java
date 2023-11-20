package com.witheat.WithEatServer.Repository;

import com.witheat.WithEatServer.Domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@EnableJpaRepositories
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    // 이걸 어디서 쓰는거람.....
    Optional<Member> findByName(String name);
}

package com.witheat.WithEatServer.Repository;

import com.witheat.WithEatServer.Domain.entity.User;
import com.witheat.WithEatServer.Domain.entity.UserWeight;
import com.witheat.WithEatServer.Domain.entity.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWeightRepository extends JpaRepository<UserWeight,Long> {

    //UserWeight 이라는 다대다 관계에 관계성을 뜻하는 엔티티 때문에 만들어진 Repository임

    //User와 체중에 대한 userWeight 조회
     List<UserWeight> findByUserAndWeight(User user, Weight weight);
}

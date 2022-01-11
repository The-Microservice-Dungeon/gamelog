package com.github.tmd.gamelog.adapter.jpa.history.robot;

import com.github.tmd.gamelog.adapter.jpa.history.robot.projections.AllRobotInfoProjection;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RobotHistoryJpaRepository extends CrudRepository<RobotHistoryJpa, UUID> {
  Set<AllRobotInfoProjection> findByRoundId(UUID roundId);
}

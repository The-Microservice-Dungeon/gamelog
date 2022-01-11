package com.github.tmd.gamelog.adapter.jpa.history.robot;

import com.github.tmd.gamelog.adapter.jpa.history.robot.projections.OnlyPlayerAndMovementDifficultyAndRobotsProjection;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementHistoryJpaRepository extends CrudRepository<MovementHistoryJpa, UUID> {
  // TODO: untested
  @Query("""
    select ch.playerId, mh.movementDifficulty, mh.robots
    from MovementHistoryJpa mh
      join CommandHistoryJpa ch on ch.transactionId = mh.transactionId
      where ch.roundId = ?1
  """)
  Set<OnlyPlayerAndMovementDifficultyAndRobotsProjection> findMovementHistoryInRound(UUID roundId);
}

package com.github.tmd.gamelog.adapter.jpa.history.robot;

import com.github.tmd.gamelog.adapter.jpa.history.robot.projections.OnlyPlayerAndBlockCountProjection;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetBlockHistoryJpaRepository extends CrudRepository<PlanetBlockHistoryJpa, UUID> {
  // TODO: Untested
  @Query("""
  select ch.playerId, count(pbh.planetId) as blockCount
    from PlanetBlockHistoryJpa pbh 
      join CommandHistoryJpa ch on ch.transactionId = pbh.transactionId
      where ch.roundId = ?1
      group by ch.playerId
  """)
  Set<OnlyPlayerAndBlockCountProjection> findPlanetBlockCountInRound(UUID roundId);
}

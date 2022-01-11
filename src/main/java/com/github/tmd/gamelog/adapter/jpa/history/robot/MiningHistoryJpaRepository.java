package com.github.tmd.gamelog.adapter.jpa.history.robot;

import com.github.tmd.gamelog.adapter.jpa.history.robot.projections.OnlyPlayerAndMinedAmountAndMinedResourceProjection;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiningHistoryJpaRepository extends CrudRepository<MiningHistoryJpa, UUID> {
  // TODO: untested
  @Query("""
    select ch.playerId as playerId, mh.minedAmount as minedAmount, mh.resourceJpa as resource
    from MiningHistoryJpa mh 
      join CommandHistoryJpa ch on ch.transactionId = mh.transactionId
      where ch.roundId = ?1
  """)
  Set<OnlyPlayerAndMinedAmountAndMinedResourceProjection> findMiningHistoryInRound(UUID roundId);
}

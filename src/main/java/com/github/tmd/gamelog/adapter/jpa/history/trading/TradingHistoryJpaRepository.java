package com.github.tmd.gamelog.adapter.jpa.history.trading;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TradingHistoryJpaRepository extends CrudRepository<TradingHistoryJpa, UUID> {
  @Query("""
    from TradingHistoryJpa pbh 
      join CommandHistoryJpa ch 
        on ch.transactionId = pbh.transactionId 
    where ch.playerId = ?1 and ch.roundId = ?2
    """)
  Set<TradingHistoryJpa> findTradingHistoryForPlayerInRound(UUID playerId, UUID roundId);

  // TODO: Maybe return a projection containing all Trading Events given a roundId with
  //       playerId already resolved
}

package com.github.tmd.gamelog.adapter.jpa.history.trading;

import com.github.tmd.gamelog.adapter.jpa.history.trading.projections.OnlyPlayerAndMoneyChangeProjection;
import com.github.tmd.gamelog.adapter.jpa.history.trading.projections.OnlyPlayerAndNumberOfTradesProjection;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TradingHistoryJpaRepository extends CrudRepository<TradingHistoryJpa, UUID> {
  @Query("""
    select ch.playerId, pbh.moneyChange
    from TradingHistoryJpa pbh 
      join CommandHistoryJpa ch 
        on ch.transactionId = pbh.transactionId 
     where ch.playerId = ?1 and ch.roundId = ?2
    """)
  Set<OnlyPlayerAndMoneyChangeProjection> findTradingHistoryForPlayerInRound(UUID playerId, UUID roundId);

  @Query("""
    select ch.playerId, pbh.moneyChange
    from TradingHistoryJpa pbh 
      join CommandHistoryJpa ch 
        on ch.transactionId = pbh.transactionId 
     where ch.roundId = ?1
    """)
  Set<OnlyPlayerAndMoneyChangeProjection> findTradingHistoryInRound(UUID roundId);

  @Query("""
  select ch.playerId, count(pbh.moneyChange)
    from TradingHistoryJpa pbh 
      join CommandHistoryJpa ch 
        on ch.transactionId = pbh.transactionId 
        where ch.roundId = ?1
     group by ch.playerId
  """)
  Set<OnlyPlayerAndNumberOfTradesProjection> findNumberOfTradesInRound(UUID roundId);
}

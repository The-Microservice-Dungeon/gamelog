package com.github.tmd.gamelog.adapter.jpa.history.trading;

import com.github.tmd.gamelog.adapter.jpa.history.trading.projections.OnlyPlayerAndBalanceProjection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerBalanceHistoryJpaRepository extends CrudRepository<PlayerBalanceHistoryJpa, UUID> {
  @Query("select pbh.playerId, pbh.balance from PlayerBalanceHistoryJpa pbh where pbh.roundId = ?1")
  Set<OnlyPlayerAndBalanceProjection> findAllBalancesInRound(UUID roundId);
}

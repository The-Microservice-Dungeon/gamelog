package com.github.tmd.gamelog.adapter.jpa.history.trading;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerBalanceHistoryJpaRepository extends CrudRepository<PlayerBalanceHistoryJpa, UUID> {
  @Query("from PlayerBalanceHistoryJpa pbh where pbh.playerId = ?1 and pbh.roundId = ?2")
  Optional<PlayerBalanceHistoryJpa> findBalanceForPlayerInRound(UUID playerId, UUID roundId);

  @Query("from PlayerBalanceHistoryJpa pbh where pbh.roundId = ?1")
  Set<PlayerBalanceHistoryJpa> findAllBalancesInRound(UUID roundId);
}

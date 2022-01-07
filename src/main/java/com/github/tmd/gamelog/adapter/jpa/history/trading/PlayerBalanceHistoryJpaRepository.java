package com.github.tmd.gamelog.adapter.jpa.history.trading;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerBalanceHistoryJpaRepository extends
    JpaRepository<PlayerBalanceHistoryJpa, UUID> {
  @Query("from PlayerBalanceHistoryJpa pbh where pbh.playerId = ?1 and pbh.assumedRoundId = ?2")
  Optional<PlayerBalanceHistoryJpa> findBalanceForPlayerInRound(UUID playerId, UUID roundId);

  @Query("from PlayerBalanceHistoryJpa pbh where pbh.assumedRoundId = ?1")
  Set<PlayerBalanceHistoryJpa> findAllBalancesInRound(UUID roundId);
}

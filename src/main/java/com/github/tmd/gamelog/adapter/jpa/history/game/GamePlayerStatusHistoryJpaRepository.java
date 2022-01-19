package com.github.tmd.gamelog.adapter.jpa.history.game;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePlayerStatusHistoryJpaRepository extends CrudRepository<GamePlayerStatusHistoryJpa, UUID> {
  @Query("select p.userId from GamePlayerStatusHistoryJpa p where p.gameId = ?1")
  Set<UUID> findAllParticipatingPlayersInGame(UUID gameId);
}

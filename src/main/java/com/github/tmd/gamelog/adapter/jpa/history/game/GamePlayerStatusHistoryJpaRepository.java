package com.github.tmd.gamelog.adapter.jpa.history.game;

import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePlayerStatusHistoryJpaRepository extends CrudRepository<GamePlayerStatusHistoryJpa, UUID> {
  @Query("""
      select p.userId from GamePlayerStatusHistoryJpa p 
        where p.gameId = ?1 
        and p.createdDate in (
          select max(p.createdDate) from GamePlayerStatusHistoryJpa where p.gameId = ?1
        )
        and p.status = "JOINED"
        """)
  Set<UUID> getAllParticipatingPlayersInGame(UUID gameId);
}

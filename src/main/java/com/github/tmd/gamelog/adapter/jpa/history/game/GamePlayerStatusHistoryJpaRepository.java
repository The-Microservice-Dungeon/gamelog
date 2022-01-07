package com.github.tmd.gamelog.adapter.jpa.history.game;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePlayerStatusHistoryJpaRepository extends
    JpaRepository<GamePlayerStatusHistoryJpa, UUID> {
  /*@Query(value = """
      WITH ranked_history
           AS (SELECT m.*,
                      Row_number()
                        OVER (
                          partition BY user_id
                          ORDER BY created_at DESC) AS rn
               FROM   game_player_status_history_jpa AS m
               WHERE m.game_id = ?1
               )
      SELECT *
      FROM   ranked_history
      WHERE  rn = 1
             AND status = 'JOINED';
        """, nativeQuery = true)
  Set<GamePlayerStatusHistoryJpa> findAllParticipatingPlayersInGame(String gameId);
  default Set<GamePlayerStatusHistoryJpa> findAllParticipatingPlayersInGame(UUID gameId) {
    return this.findAllParticipatingPlayersInGame(gameId.toString());
  }*/
  // TODO: This does not take in concern when a player joins and then leaves the game. (See above for an attempt to solve this issue)
  //       Note that if you try to fix this issue you need to modify the test
  @Query("select p.userId from GamePlayerStatusHistoryJpa p where p.gameId = ?1 and p.status = 'JOINED'")
  Set<UUID> findAllParticipatingPlayersInGame(UUID gameId);
}

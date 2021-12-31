package com.github.tmd.gamelog.adapter.jpa.scoreboard;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreboardJpaRepository extends CrudRepository<ScoreboardJpa, UUID> {
  @Query("select s from ScoreboardJpa s join s.gameJpa g where g.gameId = ?1")
  Optional<ScoreboardJpa> findByGameId(UUID gameId);
}

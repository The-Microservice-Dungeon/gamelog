package com.github.tmd.gamelog.adapter.jpa.game;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameJpaRepository extends CrudRepository<GameJpa, UUID> {
  @Query("select g from GameJpa g join g.rounds r where r.roundId = ?1 ")
  Optional<GameJpa> findGameJpaByRounds_RoundId(UUID roundId);
}

package com.github.tmd.gamelog.adapter.jpa.history.game;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameStatusHistoryJpaRepository extends CrudRepository<GameStatusHistoryJpa, UUID> {
  Optional<GameStatusHistoryJpa> findByGameId(UUID gameId);
  Optional<GameStatusHistoryJpa> findFirstByStatus(GameStatusJpa status);
}

package com.github.tmd.gamelog.adapter.jpa.history.game;

import java.util.Set;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePlayerStatusHistoryJpaRepository extends CrudRepository<GamePlayerStatusHistoryJpa, UUID> {
}

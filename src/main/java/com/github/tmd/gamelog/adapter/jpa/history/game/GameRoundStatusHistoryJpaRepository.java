package com.github.tmd.gamelog.adapter.jpa.history.game;

import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRoundStatusHistoryJpaRepository extends
    JpaRepository<GameRoundStatusHistoryJpa, UUID> {
}

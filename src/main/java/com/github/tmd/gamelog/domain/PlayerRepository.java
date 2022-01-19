package com.github.tmd.gamelog.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

public interface PlayerRepository {
    List<Player> findAll();
    Optional<Player> findById(UUID playerId);
    void upsert(Player player);
}

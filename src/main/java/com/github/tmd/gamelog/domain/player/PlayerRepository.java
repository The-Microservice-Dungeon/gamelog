package com.github.tmd.gamelog.domain.player;

import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository {
  Player save(Player player);
  Optional<Player> findById(UUID playerId);
}

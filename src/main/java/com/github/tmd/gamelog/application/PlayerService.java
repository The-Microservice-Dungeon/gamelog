package com.github.tmd.gamelog.application;

import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.PlayerRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
  private final PlayerRepository playerRepository;

  public PlayerService(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  public void createOrUpdatePlayer(UUID playerId, String name) {
    var playerToSave = this.playerRepository.findById(playerId)
        .map(player -> {
          player.setName(name);
          return player;
        })
        .orElse(new Player(playerId, name));

    this.playerRepository.upsert(playerToSave);
  }
}

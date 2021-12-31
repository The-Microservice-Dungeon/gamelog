package com.github.tmd.gamelog.domain.player;

import com.github.tmd.gamelog.domain.player.Player.PlayerId;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
  private final PlayerRepository playerRepository;

  @Autowired
  public PlayerService(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  public Player registerPlayer(UUID playerId, String username) {
    var foundPlayer = playerRepository.findById(playerId);

    if(foundPlayer.isEmpty()) {
      var player = new Player(new PlayerId(playerId), username);
      return this.playerRepository.save(player);
    }

    var player = foundPlayer.get();

    if(!player.getUserName().equals(username)) {
      player.setUserName(username);
      return this.playerRepository.save(player);
    }

    return player;
  }

  public Player getPlayerById(UUID playerId) {
    return playerRepository.findById(playerId).orElseThrow();
  }
}

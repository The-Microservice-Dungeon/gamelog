package com.github.tmd.gamelog.adapter.jpa.player;

import com.github.tmd.gamelog.domain.player.Player;
import com.github.tmd.gamelog.domain.player.Player.PlayerId;
import org.springframework.stereotype.Service;

@Service
public class PlayerMapper {

  public Player toDomain(PlayerJpa playerJpa) {
    return new Player(new PlayerId(playerJpa.getPlayerId()), playerJpa.getUserName());
  }

  public PlayerJpa toPersistence(Player player) {
    var jpa = new PlayerJpa();
    jpa.setPlayerId(player.getId().playerId());
    if(player.getUserName() == null) {
      jpa.setUserName(player.getId().playerId().toString());
    } else {
      jpa.setUserName(player.getUserName());
    }
    return jpa;
  }
}

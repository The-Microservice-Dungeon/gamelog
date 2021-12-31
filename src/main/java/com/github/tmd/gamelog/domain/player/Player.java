package com.github.tmd.gamelog.domain.player;

import java.util.UUID;
import lombok.Data;

@Data
public class Player {
  private final PlayerId id;

  private String userName;

  public Player(PlayerId id, String userName) {
    this.id = id;
    this.userName = userName;
  }

  public record PlayerId(UUID playerId) {}
}

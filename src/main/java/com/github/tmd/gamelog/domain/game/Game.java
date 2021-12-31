package com.github.tmd.gamelog.domain.game;

import java.util.UUID;
import lombok.Data;

@Data
public class Game {
  private final GameId id;

  private GameStatus gameStatus = GameStatus.CREATED;

  public record GameId(UUID gameId) {}
}

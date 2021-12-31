package com.github.tmd.gamelog.domain.game;

import lombok.Data;

@Data
public class Game {
  private final GameId id;

  private GameStatus gameStatus = GameStatus.CREATED;

  public record GameId(String gameId) {}
}

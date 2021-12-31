package com.github.tmd.gamelog.domain.scoreboard.model;

import java.util.Set;
import java.util.UUID;
import lombok.Data;

@Data
public class Game {
  private final GameId id;

  private GameStatus gameStatus = GameStatus.CREATED;

  private final Set<Round> rounds;

  public void addRound(Round round) {
    this.rounds.add(round);
  }

  public record GameId(UUID gameId) {}
}

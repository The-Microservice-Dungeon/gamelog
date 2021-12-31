package com.github.tmd.gamelog.domain.game;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Data;

@Data
public class Game {
  private final GameId id;

  private GameStatus gameStatus = GameStatus.CREATED;

  private final Set<Round> rounds = new HashSet<>();

  public void addRound(Round round) {
    this.rounds.add(round);
  }

  public record GameId(UUID gameId) {}
}

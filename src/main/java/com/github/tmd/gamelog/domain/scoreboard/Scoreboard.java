package com.github.tmd.gamelog.domain.scoreboard;

import com.github.tmd.gamelog.domain.game.Game;
import java.util.UUID;
import lombok.Data;

@Data
public class Scoreboard {
  private final ScoreboardId scoreboardId;
  private final Game game;

  public record ScoreboardId(UUID id) {}
}

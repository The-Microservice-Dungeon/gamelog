package com.github.tmd.gamelog.domain.scoreboard.model;

import com.github.tmd.gamelog.domain.player.Player;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.Data;

@Data
public class Scoreboard {
  private final ScoreboardId scoreboardId;
  private final Game game;
  private final Map<Player, Set<RoundScore>> roundScores;

  public record ScoreboardId(UUID id) {}
}

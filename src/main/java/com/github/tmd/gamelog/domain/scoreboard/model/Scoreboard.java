package com.github.tmd.gamelog.domain.scoreboard.model;

import com.github.tmd.gamelog.domain.player.Player;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Scoreboard {
  private final ScoreboardId scoreboardId;
  private final Game game;
  private final Map<Player, PlayerGameScore> roundScores;
  @Setter(AccessLevel.NONE)
  private ScoreboardStatus status = ScoreboardStatus.OPEN;

  public void lock() {
    this.status = ScoreboardStatus.LOCKED;
  }

  public record ScoreboardId(UUID id) {}
}

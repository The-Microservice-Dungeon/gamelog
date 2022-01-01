package com.github.tmd.gamelog.domain.scoreboard.model;

import com.github.tmd.gamelog.domain.player.Player;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.Data;

@Data
public class Scoreboard {
  private final ScoreboardId scoreboardId;
  private final Game game;
  private final Map<Player, Set<RoundScore>> roundScores;

  public void addRoundScore(Player player, RoundScore score) {
    var existingRoundScores = roundScores.getOrDefault(player, Set.of());
    var isRoundScoreAlreadySet = existingRoundScores
        .stream().anyMatch(rs -> rs.round().equals(score.round()));

    if(isRoundScoreAlreadySet) throw new RuntimeException("Cannot update score, it is fixed once its set");

    var newRoundScores = new HashSet<>(existingRoundScores);
    newRoundScores.add(score);

    this.roundScores.put(player, Set.copyOf(newRoundScores));
  }

  public record ScoreboardId(UUID id) {}
}

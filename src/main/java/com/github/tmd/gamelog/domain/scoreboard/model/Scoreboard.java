package com.github.tmd.gamelog.domain.scoreboard.model;

import com.github.tmd.gamelog.domain.player.Player;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
  private final Map<Player, Set<RoundScore>> roundScores;
  @Setter(AccessLevel.NONE)
  private ScoreboardStatus status = ScoreboardStatus.OPEN;

  public void addRoundScore(Player player, UUID roundId) {
    if(status == ScoreboardStatus.LOCKED) throw new RuntimeException("Cannot update locked scoreboard");

    var round = this.game.getRounds().stream()
        .filter(r -> r.getRoundId().roundId().equals(roundId))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Round is not registered in game"));

    var existingRoundScores = roundScores.getOrDefault(player, Set.of());
    var isRoundScoreAlreadySet = existingRoundScores
        .stream().anyMatch(rs -> rs.round().equals(roundId));

    if(isRoundScoreAlreadySet) throw new RuntimeException("Cannot update score, it is fixed once its set");

    var newRoundScores = new HashSet<>(existingRoundScores);
    newRoundScores.add(new RoundScore(round));

    this.roundScores.put(player, Set.copyOf(newRoundScores));
  }

  public void lock() {
    this.status = ScoreboardStatus.LOCKED;
  }

  public record ScoreboardId(UUID id) {}
}

package com.github.tmd.gamelog.domain.score.entity;

import com.github.tmd.gamelog.domain.Game;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.score.vo.AggregatedScore;
import com.google.inject.internal.util.Iterables;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Scoreboard {
  private final Game game;
  private final Map<Player, AggregatedScore> gameScores;
  private final Map<Player, List<AggregatedScore>> roundScores;

  // TODO: Some hacky and exhausting methods for retrieving the placement of a player, they need
  //  refactoring
  public int getTotalPlacementOfPlayer(Player player) {
    var totalScore = gameScores.get(player).score();
    var numberOfPlayersWithMorePoints = gameScores.values().stream()
        .filter(s -> s.score() > totalScore).count();

    return 1 + (int) numberOfPlayersWithMorePoints;
  }

  public int getFightingPlacementOfPlayer(Player player) {
    var fightingScore = gameScores.get(player).getFightingScore();
    var numberOfPlayersWithMorePoints = gameScores.values().stream()
        .filter(s -> s.getFightingScore() > fightingScore).count();

    return 1 + (int) numberOfPlayersWithMorePoints;
  }

  public int getMiningPlacementOfPlayer(Player player) {
    var miningScore = gameScores.get(player).getMiningScore();
    var numberOfPlayersWithMorePoints = gameScores.values().stream()
        .filter(s -> s.getMiningScore() > miningScore).count();

    return 1 + (int) numberOfPlayersWithMorePoints;
  }

  public int getMovementPlacementOfPlayer(Player player) {
    var movementScore = gameScores.get(player).getMovementScore();
    var numberOfPlayersWithMorePoints = gameScores.values().stream()
        .filter(s -> s.getMovementScore() > movementScore).count();

    return 1 + (int) numberOfPlayersWithMorePoints;
  }

  public int getRobotPlacementOfPlayer(Player player) {
    var robotScore = gameScores.get(player).getRobotScore();
    var numberOfPlayersWithMorePoints = gameScores.values().stream()
        .filter(s -> s.getRobotScore() > robotScore).count();

    return 1 + (int) numberOfPlayersWithMorePoints;
  }

  public int getTradingPlacementOfPlayer(Player player) {
    var tradingScore = gameScores.get(player).getTradingScore();
    var numberOfPlayersWithMorePoints = gameScores.values().stream()
        .filter(s -> s.getTradingScore() > tradingScore).count();

    return 1 + (int) numberOfPlayersWithMorePoints;
  }
}

package com.github.tmd.gamelog.application.score.accumulator;

import com.github.tmd.gamelog.application.score.AggregatedRoundScore;
import com.github.tmd.gamelog.application.score.GameScore;
import com.github.tmd.gamelog.application.score.RoundScoreService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GameScoreAccumulator  {
  private final RoundScoreService roundScoreService;

  public GameScoreAccumulator(
      RoundScoreService roundScoreService) {
    this.roundScoreService = roundScoreService;
  }

  public Map<UUID, GameScore> accumulateGameScore(UUID gameId) {
    return this.roundScoreService.getAllOrderedAggregatedScoresInGame(gameId)
        .entrySet().stream()
        .collect(Collectors.toMap(
            c -> c.getKey(),
            c -> this.calculateGameScore(c.getValue())
        ));
  }

  private GameScore calculateGameScore(List<AggregatedRoundScore> aggregatedRoundScores) {
    var rawScore = 0.0;

    for(var aggRoundScore : aggregatedRoundScores) {
      rawScore += aggRoundScore.score();
    }

    return new GameScore(rawScore);
  }
}

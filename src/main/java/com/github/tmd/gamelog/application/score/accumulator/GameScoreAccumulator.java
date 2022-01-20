package com.github.tmd.gamelog.application.score.accumulator;

import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.score.vo.AggregatedRoundScore;
import com.github.tmd.gamelog.domain.score.vo.AggregatedGameScore;
import com.github.tmd.gamelog.application.score.service.RoundScoreService;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

  public Map<Player, AggregatedGameScore> accumulateGameScore(UUID gameId) {
    return this.roundScoreService.getAllOrderedAggregatedScoresInGame(gameId)
        .entrySet().stream()
        .collect(Collectors.toMap(
            Entry::getKey,
            c -> this.calculateGameScore(c.getValue())
        ));
  }

  private AggregatedGameScore calculateGameScore(List<AggregatedRoundScore> aggregatedRoundScores) {
    var rawScore = 0.0;

    for(var aggRoundScore : aggregatedRoundScores) {
      rawScore += aggRoundScore.score();
    }

    return new AggregatedGameScore(rawScore);
  }
}

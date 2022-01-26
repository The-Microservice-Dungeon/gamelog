package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.score.vo.AggregatedScore;
import com.github.tmd.gamelog.application.score.service.RoundScoreService;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GameScoreAggregator {
  private final RoundScoreService roundScoreService;

  public GameScoreAggregator(
      RoundScoreService roundScoreService) {
    this.roundScoreService = roundScoreService;
  }

  public Map<Player, AggregatedScore> aggregateGameScore(UUID gameId) {
    log.debug("Aggregating game scores for game with Id {}", gameId);
    var gameScores = this.roundScoreService.getAllOrderedAggregatedScoresInGame(gameId)
        .entrySet().stream()
        .collect(Collectors.toMap(
            Entry::getKey,
            c -> this.calculateGameScore(c.getValue())
        ));

    if(log.isDebugEnabled()) {
      for(var entry : gameScores.entrySet()) {
        log.debug("Aggregated score {} for player {}", entry.getValue(), entry.getKey());
      }
    }

    return gameScores;
  }

  private AggregatedScore calculateGameScore(List<AggregatedScore> aggregatedScores) {
    return aggregatedScores.stream().reduce(AggregatedScore.builder().build(), (aggregatedScore, aggregatedScore2) -> {
      Double fightingScore = aggregatedScore.getFightingScore() + aggregatedScore2.getFightingScore();
      Double miningScore = aggregatedScore.getFightingScore() + aggregatedScore2.getFightingScore();
      Double movementScore = aggregatedScore.getFightingScore() + aggregatedScore2.getFightingScore();
      Double robotScore = aggregatedScore.getFightingScore() + aggregatedScore2.getFightingScore();
      Double tradingScore = aggregatedScore.getFightingScore() + aggregatedScore2.getFightingScore();

      return AggregatedScore.builder().fightingScore(fightingScore)
          .miningScore(miningScore)
          .movementScore(movementScore)
          .robotScore(robotScore)
          .tradingScore(tradingScore)
          .build();
    });
  }
}

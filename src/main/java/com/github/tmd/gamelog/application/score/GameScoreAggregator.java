package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.application.history.GameHistoryService;
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
  private final GameHistoryService gameHistoryService;

  public GameScoreAggregator(
      RoundScoreService roundScoreService,
      GameHistoryService gameHistoryService) {
    this.roundScoreService = roundScoreService;
    this.gameHistoryService = gameHistoryService;
  }

  public Map<Player, AggregatedScore> aggregateGameScore(UUID gameId) {
    log.trace("Aggregating game scores for game with Id {}", gameId);
    var participating = gameHistoryService.getAllParticipatingPlayersInGame(gameId);
    var gameScores = this.roundScoreService.getAllOrderedAggregatedScoresInGame(gameId)
        .entrySet().stream()
        .filter(entry -> participating.contains(entry.getKey().getId())) // Include only players that are playing
        .collect(Collectors.toMap(
            Entry::getKey,
            c -> this.calculateGameScore(c.getValue())
        ));

    if(log.isDebugEnabled()) {
      for(var entry : gameScores.entrySet()) {
        log.trace("Aggregated score {} for player {}", entry.getValue(), entry.getKey());
      }
    }

    return gameScores;
  }

  private AggregatedScore calculateGameScore(List<AggregatedScore> aggregatedScores) {
    Double sumFightingScore = aggregatedScores.stream()
        .mapToDouble(AggregatedScore::getFightingScore).sum();
    Double sumMiningScore = aggregatedScores.stream().mapToDouble(AggregatedScore::getMiningScore)
        .sum();
    Double sumMovementScore = aggregatedScores.stream()
        .mapToDouble(AggregatedScore::getMovementScore).sum();
    Double sumRobotScore = aggregatedScores.stream().mapToDouble(AggregatedScore::getRobotScore)
        .sum();
    Double sumTradingScore = aggregatedScores.stream().mapToDouble(AggregatedScore::getTradingScore)
        .sum();

    return AggregatedScore.builder()
        .fightingScore(sumFightingScore)
        .miningScore(sumMiningScore)
        .movementScore(sumMovementScore)
        .robotScore(sumRobotScore)
        .tradingScore(sumTradingScore)
        .build();
  }
}

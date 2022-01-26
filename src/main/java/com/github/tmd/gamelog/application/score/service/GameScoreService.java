package com.github.tmd.gamelog.application.score.service;

import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.application.score.GameScoreAggregator;
import com.github.tmd.gamelog.domain.score.core.ScoreCategory;
import com.github.tmd.gamelog.domain.score.vo.AggregatedScore;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GameScoreService {
  private final GameScoreAggregator gameScoreAggregator;

  public GameScoreService(
      GameScoreAggregator gameScoreAggregator) {
    this.gameScoreAggregator = gameScoreAggregator;
  }

  public Map<Player, AggregatedScore> getScoresInGame(UUID gameId) {
    return gameScoreAggregator.aggregateGameScore(gameId);
  }

  public Map<Player, AggregatedScore> getScoresOfCategoryInGame(UUID gameId, ScoreCategory category) {
    return gameScoreAggregator.aggregateGameScore(gameId);
  }
}

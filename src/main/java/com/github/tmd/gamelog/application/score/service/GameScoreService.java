package com.github.tmd.gamelog.application.score.service;

import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.score.vo.AggregatedGameScore;
import com.github.tmd.gamelog.application.score.accumulator.GameScoreAccumulator;
import com.github.tmd.gamelog.domain.score.core.ScoreCategory;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GameScoreService {
  private final GameScoreAccumulator gameScoreAccumulator;

  public GameScoreService(
      GameScoreAccumulator gameScoreAccumulator) {
    this.gameScoreAccumulator = gameScoreAccumulator;
  }

  public Map<Player, AggregatedGameScore> getScoresInGame(UUID gameId) {
    return gameScoreAccumulator.accumulateGameScore(gameId);
  }

  public Map<Player, AggregatedGameScore> getScoresOfCategoryInGame(UUID gameId, ScoreCategory category) {
    return gameScoreAccumulator.accumulateGameScore(gameId);
  }
}

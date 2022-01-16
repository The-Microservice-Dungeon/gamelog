package com.github.tmd.gamelog.application.score.service;

import com.github.tmd.gamelog.domain.score.entity.GameScore;
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

  public Map<UUID, GameScore> getScoresInGame(UUID gameId) {
    return gameScoreAccumulator.accumulateGameScore(gameId);
  }

  public Map<UUID, GameScore> getScoresOfCategoryInGame(UUID gameId, ScoreCategory category) {
    return gameScoreAccumulator.accumulateGameScore(gameId);
  }
}

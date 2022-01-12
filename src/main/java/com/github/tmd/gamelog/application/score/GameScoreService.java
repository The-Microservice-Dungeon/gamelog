package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.application.score.accumulator.GameScoreAccumulator;
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
}

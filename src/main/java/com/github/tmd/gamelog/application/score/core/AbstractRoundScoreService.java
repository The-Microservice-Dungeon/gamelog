package com.github.tmd.gamelog.application.score.core;

import com.github.tmd.gamelog.application.score.trading.TradingRoundScore;
import java.util.UUID;

public abstract class AbstractRoundScoreService<T extends ScorableRoundScore> {
  public abstract T getRoundScoreForPlayerInRound(UUID playerId, UUID roundId);
}

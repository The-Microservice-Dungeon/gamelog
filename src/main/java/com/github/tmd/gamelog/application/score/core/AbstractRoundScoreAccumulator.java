package com.github.tmd.gamelog.application.score.core;

import java.util.Map;
import java.util.UUID;

public abstract class AbstractRoundScoreAccumulator<T extends ScorableRoundScore> {
  public abstract Map<UUID, T> accumulateRoundScores(UUID roundId);
}

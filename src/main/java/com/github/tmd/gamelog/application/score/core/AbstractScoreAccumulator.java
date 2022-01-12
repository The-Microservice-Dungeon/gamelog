package com.github.tmd.gamelog.application.score.core;

import com.github.tmd.gamelog.domain.score.core.ScorableScore;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractScoreAccumulator<T extends ScorableScore> {
  public abstract Map<UUID, T> accumulateScoreInRound(UUID roundId);
}

package com.github.tmd.gamelog.application.score.core;

import com.github.tmd.gamelog.domain.score.core.CategorizableScore;
import com.github.tmd.gamelog.domain.score.core.ScorableScore;
import com.github.tmd.gamelog.domain.score.core.ScoreCategory;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractCategorizedRoundScoreAccumulator<T extends CategorizableScore> extends AbstractScoreAccumulator<T> {
  public abstract Map<UUID, T> accumulateRoundScores(UUID roundId);

  @Override
  public Map<UUID, T> accumulateScoreInRound(UUID roundId) {
    return accumulateRoundScores(roundId);
  }
}

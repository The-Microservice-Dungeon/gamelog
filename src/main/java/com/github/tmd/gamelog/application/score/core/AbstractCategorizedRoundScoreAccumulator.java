package com.github.tmd.gamelog.application.score.core;

import com.github.tmd.gamelog.domain.score.core.CategorizableScore;
import com.github.tmd.gamelog.domain.score.core.ScorableScore;
import com.github.tmd.gamelog.domain.score.core.ScoreCategory;
import java.util.Map;
import java.util.UUID;

/**
 * Defines a round score Accumulator that is capable of accumulating a specific score category.
 *
 * TODO: This can be a interface aswell, however the Accumulators contain some manual work that can
 *       maybe extracted into this abstract class. This is a refactoring candidate. However, we
 *       still need to define some tests before
 * @param <T> The Categorized Score
 */
public abstract class AbstractCategorizedRoundScoreAccumulator<T extends CategorizableScore>  {

  /**
   * Accumulate the round scores for a given Round
   * @param roundId Round ID
   * @return Player ID as key, Round Score as value
   */
  public abstract Map<UUID, T> accumulateRoundScores(UUID roundId);
}

package com.github.tmd.gamelog.application.score.core;

import com.github.tmd.gamelog.domain.score.core.ScorableScore;

/**
 * Describes a round score that is accumulatable. Hence it contains different values
 * that can be combined (=accumulated) to a score
 * @param <T> the type that will be accumulated.
 */
public interface AccumulatableRoundScore<T extends Number> extends ScorableScore {
  T accumulate();

  @Override
  default Double score() {
    return this.accumulate().doubleValue();
  }
}

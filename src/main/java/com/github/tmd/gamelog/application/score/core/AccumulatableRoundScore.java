package com.github.tmd.gamelog.application.score.core;

/**
 * Describes a round score that is accumulatable. Hence it contains different values
 * that can be combined (=accumulated) to a score
 * @param <T> the type that will be accumulated.
 */
public interface AccumulatableRoundScore<T extends Number> extends ScorableRoundScore {
  T accumulate();

  @Override
  default Double rawRoundScore() {
    return this.accumulate().doubleValue();
  }
}

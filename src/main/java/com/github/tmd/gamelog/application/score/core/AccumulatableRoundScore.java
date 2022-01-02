package com.github.tmd.gamelog.application.score.core;

/**
 * Describes a round score that is accumulatable. Hence it contains different values
 * that can be combined (=accumulated) to a score
 * @param <T> the type that will be accumulated.
 */
public interface AccumulatableRoundScore<T extends Number> {
  T accumulate();
}

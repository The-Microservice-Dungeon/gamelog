package com.github.tmd.gamelog.application.score.core;

/**
 * Describes a Score Attribute that can be reduced to some kind of numeric value
 * @param <T> the numeric type
 */
public interface ValuebleScoreAttribute<T extends Number> {
  T value();
}

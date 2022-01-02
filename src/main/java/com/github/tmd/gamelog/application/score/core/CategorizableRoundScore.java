package com.github.tmd.gamelog.application.score.core;

/**
 * Describes a score that belongs to a specific category
 */
public interface CategorizableRoundScore {
  RoundScoreCategory category();
}

package com.github.tmd.gamelog.domain.score.core;

/**
 * Describes a score that belongs to a specific category
 */
public interface CategorizableScore extends ScorableScore {
  ScoreCategory category();
}

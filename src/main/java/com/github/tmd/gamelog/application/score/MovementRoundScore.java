package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.domain.score.core.CategorizableScore;
import com.github.tmd.gamelog.domain.score.core.ScoreCategory;
import com.github.tmd.gamelog.domain.score.core.ScorableScore;

public record MovementRoundScore(
    Integer passedDifficulty
) implements CategorizableScore, ScorableScore {

  @Override
  public ScoreCategory category() {
    return ScoreCategory.MOVEMENT;
  }

  @Override
  public Double score() {
    return passedDifficulty.doubleValue();
  }
}

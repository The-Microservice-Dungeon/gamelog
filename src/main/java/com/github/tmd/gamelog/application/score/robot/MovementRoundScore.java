package com.github.tmd.gamelog.application.score.robot;

import com.github.tmd.gamelog.application.score.core.AccumulatableRoundScore;
import com.github.tmd.gamelog.application.score.core.CategorizableRoundScore;
import com.github.tmd.gamelog.application.score.core.RoundScoreCategory;
import com.github.tmd.gamelog.application.score.core.ScorableRoundScore;

public record MovementRoundScore(
    Integer passedDifficulty
) implements CategorizableRoundScore, ScorableRoundScore {



  @Override
  public RoundScoreCategory category() {
    return RoundScoreCategory.MOVEMENT;
  }

  @Override
  public Double score() {
    return passedDifficulty.doubleValue();
  }
}

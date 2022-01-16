package com.github.tmd.gamelog.domain.score.vo;

import com.github.tmd.gamelog.domain.score.core.AccumulatableRoundScore;
import com.github.tmd.gamelog.domain.score.core.CategorizableScore;
import com.github.tmd.gamelog.domain.score.core.ScoreCategory;
import java.util.List;

public record RobotRoundScore(
    List<RobotLevelsScoreAttribute> robotLevels
) implements AccumulatableRoundScore<Integer>,
    CategorizableScore {

  @Override
  public Integer accumulate() {
    var levelScore = 0;

    for(var rlt : robotLevels) {
      levelScore += rlt.value();
    }

    return levelScore;
  }

  @Override
  public ScoreCategory category() {
    return ScoreCategory.ROBOT;
  }
}

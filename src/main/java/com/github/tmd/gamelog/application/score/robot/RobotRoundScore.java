package com.github.tmd.gamelog.application.score.robot;

import com.github.tmd.gamelog.application.__tmpstructs.RobotLevelsThingy;
import com.github.tmd.gamelog.application.score.core.AccumulatableRoundScore;
import com.github.tmd.gamelog.application.score.core.CategorizableRoundScore;
import com.github.tmd.gamelog.application.score.core.RoundScoreCategory;
import java.util.List;
import java.util.Set;

public record RobotRoundScore(
    List<RobotLevelsThingy> robotLevels
) implements AccumulatableRoundScore<Integer>,
    CategorizableRoundScore {

  @Override
  public Integer accumulate() {
    var levelScore = 0;

    for(var rlt : robotLevels) {
      levelScore += rlt.value();
    }

    return levelScore;
  }

  @Override
  public RoundScoreCategory category() {
    return RoundScoreCategory.ROBOT;
  }
}

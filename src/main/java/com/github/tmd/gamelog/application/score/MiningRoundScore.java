package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.application.score.core.AccumulatableRoundScore;
import com.github.tmd.gamelog.domain.score.core.CategorizableScore;
import com.github.tmd.gamelog.domain.score.core.ScoreCategory;
import java.util.List;

public record MiningRoundScore(
    List<ResourceMinedScoreAttribute> minedAmounts
) implements CategorizableScore, AccumulatableRoundScore {

  @Override
  public Number accumulate() {
    var miningScore = 0;

    for(var mining : minedAmounts) {
      miningScore += mining.value();
    }

    return miningScore;
  }

  @Override
  public ScoreCategory category() {
    return ScoreCategory.MINING;
  }
}

package com.github.tmd.gamelog.application.score.robot;

import com.github.tmd.gamelog.adapter.event.gameEvent.map.ResourceType;
import com.github.tmd.gamelog.application.__tmpstructs.ResourceMinedThingy;
import com.github.tmd.gamelog.application.score.core.AccumulatableRoundScore;
import com.github.tmd.gamelog.application.score.core.CategorizableRoundScore;
import com.github.tmd.gamelog.application.score.core.RoundScoreCategory;
import java.util.List;
import java.util.Map;
import java.util.Set;

public record MiningRoundScore(
    List<ResourceMinedThingy> minedAmounts
) implements CategorizableRoundScore, AccumulatableRoundScore {

  @Override
  public Number accumulate() {
    var miningScore = 0;

    for(var mining : minedAmounts) {
      miningScore += mining.value();
    }

    return miningScore;
  }

  @Override
  public RoundScoreCategory category() {
    return RoundScoreCategory.MINING;
  }
}

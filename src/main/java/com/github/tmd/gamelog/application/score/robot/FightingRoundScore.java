package com.github.tmd.gamelog.application.score.robot;

import com.github.tmd.gamelog.application.score.core.AccumulatableRoundScore;
import com.github.tmd.gamelog.application.score.core.CategorizableRoundScore;
import com.github.tmd.gamelog.application.score.core.RoundScoreCategory;

public record FightingRoundScore(
    Integer numOfPerformedKills,
    Integer numOfVictims,
    Integer numOfPerformedDamage
) implements CategorizableRoundScore, AccumulatableRoundScore<Integer> {

  @Override
  public Integer accumulate() {
    return numOfPerformedKills + numOfVictims + numOfPerformedDamage;
  }

  @Override
  public RoundScoreCategory category() {
    return RoundScoreCategory.FIGHTING;
  }
}

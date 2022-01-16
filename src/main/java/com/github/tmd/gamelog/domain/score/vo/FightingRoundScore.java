package com.github.tmd.gamelog.domain.score.vo;

import com.github.tmd.gamelog.domain.score.core.AccumulatableRoundScore;
import com.github.tmd.gamelog.domain.score.core.CategorizableScore;
import com.github.tmd.gamelog.domain.score.core.ScoreCategory;

public record FightingRoundScore(
    Integer numOfPerformedKills,
    Integer numOfVictims,
    Integer numOfPerformedDamage
) implements CategorizableScore, AccumulatableRoundScore<Integer> {

  @Override
  public Integer accumulate() {
    return numOfPerformedKills + numOfVictims + numOfPerformedDamage;
  }

  @Override
  public ScoreCategory category() {
    return ScoreCategory.FIGHTING;
  }
}

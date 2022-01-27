package com.github.tmd.gamelog.domain.score.vo;

import com.github.tmd.gamelog.domain.score.core.AccumulatableRoundScore;
import com.github.tmd.gamelog.domain.score.core.CategorizableScore;
import com.github.tmd.gamelog.domain.score.core.ScoreCategory;

public record TradingRoundScore(Integer balance, Integer numOfTrades) implements CategorizableScore, AccumulatableRoundScore<Integer> {

  @Override
  public Integer accumulate() {
    return numOfTrades * 25;
  }

  @Override
  public ScoreCategory category() {
    return ScoreCategory.TRADING;
  }
}

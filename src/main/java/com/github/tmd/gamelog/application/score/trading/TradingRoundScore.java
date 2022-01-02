package com.github.tmd.gamelog.application.score.trading;

import com.github.tmd.gamelog.application.score.core.AccumulatableRoundScore;
import com.github.tmd.gamelog.application.score.core.CategorizableRoundScore;
import com.github.tmd.gamelog.application.score.core.RoundScoreCategory;

public record TradingRoundScore(Integer balance, Integer numOfTrades) implements CategorizableRoundScore, AccumulatableRoundScore<Integer> {

  @Override
  public Integer accumulate() {
    return balance() + numOfTrades * 10;
  }

  @Override
  public RoundScoreCategory category() {
    return RoundScoreCategory.TRADING;
  }
}

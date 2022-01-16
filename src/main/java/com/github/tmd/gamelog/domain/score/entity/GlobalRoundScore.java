package com.github.tmd.gamelog.domain.score.entity;

import com.github.tmd.gamelog.domain.score.core.ScorableScore;

public record GlobalRoundScore(Double score)
  implements ScorableScore
{
  @Override
  public Double score() {
    return score;
  }
}

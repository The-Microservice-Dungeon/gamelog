package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.domain.score.core.ScorableScore;

public record GlobalRoundScore(Double score)
  implements ScorableScore
{
  @Override
  public Double rawRoundScore() {
    return score;
  }
}

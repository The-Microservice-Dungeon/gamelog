package com.github.tmd.gamelog.domain.score.core;

public interface ScorableScore {
  Double rawRoundScore();

  default Double score() {
    return weight() * rawRoundScore();
  }

  default Double weight() {
    return 1.0;
  }
}

package com.github.tmd.gamelog.application.score.core;

public interface ScorableRoundScore {
  Double rawRoundScore();

  default Double score() {
    return weight() * rawRoundScore();
  }

  default Double weight() {
    return 1.0;
  }
}

package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.application.score.core.ScorableRoundScore;

public record GlobalRoundScore(Double score)
  implements ScorableRoundScore
{ }

package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.domain.score.core.ScorableScore;
import javax.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AggregatedRoundScore implements ScorableScore {
  private final Double fightingScore;
  private final Double miningScore;
  private final Double movementScore;
  private final Double robotScore;
  private final Double tradingScore;

  @Override
  public Double score() {
    // TODO: Weights
    return fightingScore + miningScore + movementScore + robotScore + tradingScore;
  }
}

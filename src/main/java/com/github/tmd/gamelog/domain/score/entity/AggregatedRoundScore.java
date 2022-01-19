package com.github.tmd.gamelog.domain.score.entity;

import com.github.tmd.gamelog.domain.score.core.ScorableScore;
import com.github.tmd.gamelog.domain.score.core.ScoreCategory;
import javax.persistence.Column;
import lombok.Builder;
import lombok.Data;

// TODO: Introduce a strong reference to the player, game, round - if somehow possible. This is
//      a value object at the moment. 
/**
 * Aggregated Round scores where each category is represented
 */
@Builder
@Data
public class AggregatedRoundScore implements ScorableScore {
  @Builder.Default
  private final Double fightingScore = 0.0;
  @Builder.Default
  private final Double miningScore = 0.0;
  @Builder.Default
  private final Double movementScore = 0.0;
  @Builder.Default
  private final Double robotScore = 0.0;
  @Builder.Default
  private final Double tradingScore = 0.0;

  @Override
  public Double score() {
    // TODO: Weights
    return fightingScore + miningScore + movementScore + robotScore + tradingScore;
  }

  // TODO: How will the weighting be applied? Is it really necessary?
  public Double getScoreOfCategory(ScoreCategory category) {
    return switch (category) {
      case FIGHTING -> this.getFightingScore();
      case MINING -> this.getMiningScore();
      case MOVEMENT -> this.getMovementScore();
      case TRADING -> this.getTradingScore();
      case ROBOT -> this.getRobotScore();
    };
  }
}

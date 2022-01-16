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

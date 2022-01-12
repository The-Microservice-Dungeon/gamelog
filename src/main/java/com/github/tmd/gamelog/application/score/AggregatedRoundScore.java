package com.github.tmd.gamelog.application.score;

import javax.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AggregatedRoundScore {
  private final Double fightingScore;
  private final Double miningScore;
  private final Double movementScore;
  private final Double robotScore;
  private final Double tradingScore;
}

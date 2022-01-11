package com.github.tmd.gamelog.adapter.jpa.score;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RoundScoreJpaEmbeddable {
  @Column(name = "fighting_score", updatable = false)
  private Double fightingScore;

  @Column(name = "mining_score", updatable = false)
  private Double miningScore;

  @Column(name = "movement_score", updatable = false)
  private Double movementScore;

  @Column(name = "robot_score", updatable = false)
  private Double robotScore;

  @Column(name = "trading_score", updatable = false)
  private Double tradingScore;
}

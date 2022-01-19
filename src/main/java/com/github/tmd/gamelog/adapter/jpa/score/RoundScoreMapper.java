package com.github.tmd.gamelog.adapter.jpa.score;

import com.github.tmd.gamelog.domain.score.vo.AggregatedRoundScore;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Mapper for RoundScore related stuff
 */
@Component
public class RoundScoreMapper {
  public RoundScoreJpaEmbeddable toPersistence(AggregatedRoundScore aggregatedRoundScore) {
    return RoundScoreJpaEmbeddable.builder()
        .fightingScore(aggregatedRoundScore.getFightingScore())
        .miningScore(aggregatedRoundScore.getMiningScore())
        .robotScore(aggregatedRoundScore.getRobotScore())
        .movementScore(aggregatedRoundScore.getMovementScore())
        .tradingScore(aggregatedRoundScore.getTradingScore())
        .build();
  }

  public AggregatedRoundScore toAggregatedRoundScore(RoundScoreJpaEmbeddable embeddable) {
    return AggregatedRoundScore.builder()
        .fightingScore(embeddable.getFightingScore())
        .miningScore(embeddable.getMiningScore())
        .movementScore(embeddable.getMovementScore())
        .robotScore(embeddable.getRobotScore())
        .tradingScore(embeddable.getTradingScore())
        .build();
  }

  public Map<UUID, AggregatedRoundScore> toAggregatedRoundScoreMap(Map<UUID,
      RoundScoreJpaEmbeddable> embeddableMap) {
    return embeddableMap.entrySet().stream()
        .collect(Collectors.toMap(
            e -> e.getKey(),
            e -> this.toAggregatedRoundScore(e.getValue())
        ));
  }

  public Map<UUID, RoundScoreJpaEmbeddable> toPersistenceMap(Map<UUID,
      AggregatedRoundScore> embeddableMap) {
    return embeddableMap.entrySet().stream()
        .collect(Collectors.toMap(
            e -> e.getKey(),
            e -> this.toPersistence(e.getValue())
        ));
  }
}

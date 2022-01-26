package com.github.tmd.gamelog.adapter.jpa.score;

import com.github.tmd.gamelog.domain.score.vo.AggregatedScore;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Mapper for RoundScore related stuff
 */
@Component
public class RoundScoreMapper {
  public RoundScoreJpaEmbeddable toPersistence(AggregatedScore aggregatedScore) {
    return RoundScoreJpaEmbeddable.builder()
        .fightingScore(aggregatedScore.getFightingScore())
        .miningScore(aggregatedScore.getMiningScore())
        .robotScore(aggregatedScore.getRobotScore())
        .movementScore(aggregatedScore.getMovementScore())
        .tradingScore(aggregatedScore.getTradingScore())
        .build();
  }

  public AggregatedScore toAggregatedRoundScore(RoundScoreJpaEmbeddable embeddable) {
    return AggregatedScore.builder()
        .fightingScore(embeddable.getFightingScore())
        .miningScore(embeddable.getMiningScore())
        .movementScore(embeddable.getMovementScore())
        .robotScore(embeddable.getRobotScore())
        .tradingScore(embeddable.getTradingScore())
        .build();
  }

  public Map<UUID, AggregatedScore> toAggregatedRoundScoreMap(Map<String,
      RoundScoreJpaEmbeddable> embeddableMap) {
    return embeddableMap.entrySet().stream()
        .collect(Collectors.toMap(
            e -> UUID.fromString(e.getKey()),
            e -> this.toAggregatedRoundScore(e.getValue())
        ));
  }

  public Map<String, RoundScoreJpaEmbeddable> toPersistenceMap(Map<UUID,
      AggregatedScore> embeddableMap) {
    return embeddableMap.entrySet().stream()
        .collect(Collectors.toMap(
            e -> e.getKey().toString(),
            e -> this.toPersistence(e.getValue())
        ));
  }
}

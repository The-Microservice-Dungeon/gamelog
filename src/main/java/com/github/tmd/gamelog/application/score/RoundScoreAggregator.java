package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.adapter.jpa.score.RoundScoreJpa;
import com.github.tmd.gamelog.adapter.jpa.score.RoundScoreJpaEmbeddable;
import com.github.tmd.gamelog.adapter.jpa.score.RoundScoreJpaEmbeddable.RoundScoreJpaEmbeddableBuilder;
import com.github.tmd.gamelog.application.score.core.AbstractCategorizedRoundScoreAccumulator;
import com.github.tmd.gamelog.domain.score.core.CategorizableScore;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
public class RoundScoreAggregator {
  private final ObjectProvider<AbstractCategorizedRoundScoreAccumulator<CategorizableScore>> roundScoreAccumulators;

  public RoundScoreAggregator(
      ObjectProvider<AbstractCategorizedRoundScoreAccumulator<CategorizableScore>> roundScoreAccumulators) {
    this.roundScoreAccumulators = roundScoreAccumulators;
  }

  public Map<UUID, AggregatedRoundScore> accumulateAndSaveRoundScoresForRound(UUID roundId) {
    Map<UUID, AggregatedRoundScore.AggregatedRoundScoreBuilder> scoreBuilders =
        new HashMap<>();

    for(var accumulator : roundScoreAccumulators) {
      for(var accumulatedScores : accumulator.accumulateRoundScores(roundId).entrySet()) {
        var scoreBuilder = scoreBuilders.getOrDefault(accumulatedScores.getKey(),
            AggregatedRoundScore.builder());

        var actualScore = accumulatedScores.getValue();
        switch (actualScore.category()) {
          case TRADING -> scoreBuilder.tradingScore(actualScore.score());
          case ROBOT -> scoreBuilder.robotScore(actualScore.score());
          case MOVEMENT -> scoreBuilder.movementScore(actualScore.score());
          case MINING -> scoreBuilder.miningScore(actualScore.score());
          case FIGHTING -> scoreBuilder.fightingScore(actualScore.score());
        }
        scoreBuilders.put(accumulatedScores.getKey(), scoreBuilder);
      }
    }

    Map<UUID, AggregatedRoundScore> scores = scoreBuilders.entrySet().stream()
        .collect(Collectors.toMap(sb -> sb.getKey(), sb -> sb.getValue().build()));

    return scores;
  }
}

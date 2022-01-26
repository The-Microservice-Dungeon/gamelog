package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.domain.score.core.AbstractCategorizedRoundScoreAccumulator;
import com.github.tmd.gamelog.domain.score.vo.AggregatedScore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service that aggregates all categorized scores into a single Aggregation
 */
@Service
@Slf4j
public class RoundScoreAggregator {
  // Inject every categorized accumulator
  private final List<AbstractCategorizedRoundScoreAccumulator<?>> roundScoreAccumulators;

  @Autowired
  public RoundScoreAggregator(
      List<AbstractCategorizedRoundScoreAccumulator<?>> roundScoreAccumulators) {
    this.roundScoreAccumulators = roundScoreAccumulators;
  }

  /**
   * Aggregates the Scores for single round.
   * @param roundId Round ID
   * @return Map of Player ID as key and AggregatedRoundScore as value
   */
  public Map<UUID, AggregatedScore> aggregateRoundScoresForRound(UUID roundId) {
    log.debug("Aggregating round scores for round with Id {}", roundId);

    // Holds wip aggregations for each player
    Map<UUID, AggregatedScore.AggregatedScoreBuilder> scoreBuilders =
        new HashMap<>();

    // Iterate over every Accumulator
    for(var accumulator : roundScoreAccumulators) {
      // Iterate over accumulated results
      for(var accumulatedScores : accumulator.accumulateRoundScores(roundId).entrySet()) {
        // Get the wip aggregation
        var scoreBuilder = scoreBuilders.getOrDefault(accumulatedScores.getKey(),
            AggregatedScore.builder());

        // Based on the category, set the score in the aggregation
        var actualScore = accumulatedScores.getValue();
        var score = actualScore.score();
        if(score == null) {
          score = 0.0;
        }
        switch (actualScore.category()) {
          case TRADING -> scoreBuilder.tradingScore(score);
          case ROBOT -> scoreBuilder.robotScore(score);
          case MOVEMENT -> scoreBuilder.movementScore(score);
          case MINING -> scoreBuilder.miningScore(score);
          case FIGHTING -> scoreBuilder.fightingScore(score);
        }
        // Put it back in
        scoreBuilders.put(accumulatedScores.getKey(), scoreBuilder);
      }
    }

    // Build Aggregations
    var roundScores = scoreBuilders.entrySet().stream()
        .collect(Collectors.toMap(Entry::getKey, sb -> sb.getValue().build()));

    if(log.isDebugEnabled()) {
      for(var entry : roundScores.entrySet()) {
        log.debug("Aggregated score {} for player {}", entry.getValue(), entry.getKey());
      }
    }

    return roundScores;
  }
}

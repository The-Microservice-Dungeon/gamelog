package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.application.score.core.AbstractCategorizedRoundScoreAccumulator;
import com.github.tmd.gamelog.domain.score.core.CategorizableScore;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

/**
 * Service that aggregates all categorized scores into a single Aggregation
 */
@Service
public class RoundScoreAggregator {
  // Inject every categorized accumulator
  private final ObjectProvider<AbstractCategorizedRoundScoreAccumulator<CategorizableScore>> roundScoreAccumulators;

  public RoundScoreAggregator(
      ObjectProvider<AbstractCategorizedRoundScoreAccumulator<CategorizableScore>> roundScoreAccumulators) {
    this.roundScoreAccumulators = roundScoreAccumulators;
  }

  /**
   * Aggregates AND SAVES the Scores for single round.
   * @param roundId Round ID
   * @return Map of Player ID as key and AggregatedRoundScore as value
   */
  public Map<UUID, AggregatedRoundScore> aggregateAndSaveRoundScoresForRound(UUID roundId) {
    // Holds wip aggregations for each player
    Map<UUID, AggregatedRoundScore.AggregatedRoundScoreBuilder> scoreBuilders =
        new HashMap<>();

    // Iterate over every Accumulator
    for(var accumulator : roundScoreAccumulators) {
      // Iterate over accumulated results
      for(var accumulatedScores : accumulator.accumulateRoundScores(roundId).entrySet()) {
        // Get the wip aggregation
        var scoreBuilder = scoreBuilders.getOrDefault(accumulatedScores.getKey(),
            AggregatedRoundScore.builder());

        // Based on the category, set the score in the aggregation
        var actualScore = accumulatedScores.getValue();
        switch (actualScore.category()) {
          case TRADING -> scoreBuilder.tradingScore(actualScore.score());
          case ROBOT -> scoreBuilder.robotScore(actualScore.score());
          case MOVEMENT -> scoreBuilder.movementScore(actualScore.score());
          case MINING -> scoreBuilder.miningScore(actualScore.score());
          case FIGHTING -> scoreBuilder.fightingScore(actualScore.score());
        }
        // Put it back in
        scoreBuilders.put(accumulatedScores.getKey(), scoreBuilder);
      }
    }

    // Build Aggregations
    return scoreBuilders.entrySet().stream()
        .collect(Collectors.toMap(Entry::getKey, sb -> sb.getValue().build()));
  }
}

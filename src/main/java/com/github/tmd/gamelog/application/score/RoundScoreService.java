package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.adapter.jpa.score.RoundScoreJpa;
import com.github.tmd.gamelog.adapter.jpa.score.RoundScoreJpaEmbeddable;
import com.github.tmd.gamelog.adapter.jpa.score.RoundScoreJpaRepository;
import com.github.tmd.gamelog.application.score.core.AbstractCategorizedRoundScoreAccumulator;
import com.github.tmd.gamelog.domain.score.core.CategorizableScore;
import com.github.tmd.gamelog.domain.score.core.ScorableScore;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
public class RoundScoreService {
  private final RoundScoreJpaRepository roundScoreJpaRepository;
  private final ObjectProvider<AbstractCategorizedRoundScoreAccumulator<CategorizableScore>> roundScoreAccumulators;

  public RoundScoreService(
      RoundScoreJpaRepository roundScoreJpaRepository,
      ObjectProvider<AbstractCategorizedRoundScoreAccumulator<CategorizableScore>> roundScoreAccumulators) {
    this.roundScoreJpaRepository = roundScoreJpaRepository;

    this.roundScoreAccumulators = roundScoreAccumulators;
  }

  public void accumulateAndSaveRoundScoresForRound(UUID roundId) {
    var roundScoreBuilder = RoundScoreJpa.builder()
        .roundId(roundId);

    Map<UUID, RoundScoreJpaEmbeddable.RoundScoreJpaEmbeddableBuilder> scoreBuilders =
        new HashMap<>();

    for(var accumulator : roundScoreAccumulators) {
      for(var accumulatedScores : accumulator.accumulateRoundScores(roundId).entrySet()) {
        var scoreBuilder = scoreBuilders.getOrDefault(accumulatedScores.getKey(),
            RoundScoreJpaEmbeddable.builder());

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

    Map<UUID, RoundScoreJpaEmbeddable> scores = scoreBuilders.entrySet().stream()
        .collect(Collectors.toMap(sb -> sb.getKey(), sb -> sb.getValue().build()));
    roundScoreBuilder.playerRoundScores(scores);

    this.roundScoreJpaRepository.save(roundScoreBuilder.build());
  }
}

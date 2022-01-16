package com.github.tmd.gamelog.application.score.service;

import com.github.tmd.gamelog.adapter.jpa.score.RoundScoreJpa;
import com.github.tmd.gamelog.adapter.jpa.score.RoundScoreRepositoryImpl;
import com.github.tmd.gamelog.domain.score.entity.AggregatedRoundScore;
import com.github.tmd.gamelog.application.score.RoundScoreAggregator;
import com.github.tmd.gamelog.domain.score.repository.AggregatedRoundScoreRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoundScoreService {
  private final RoundScoreAggregator roundScoreAggregator;
  private final AggregatedRoundScoreRepository roundScoreRepository;


  public RoundScoreService(
      RoundScoreAggregator roundScoreAggregator,
      AggregatedRoundScoreRepository roundScoreRepository) {
    this.roundScoreAggregator = roundScoreAggregator;
    this.roundScoreRepository = roundScoreRepository;
  }

  public Map<UUID, List<AggregatedRoundScore>> getAllOrderedAggregatedScoresInGame(UUID gameId) {
    try {
      var result = this.roundScoreRepository.findAllOrderedRoundScoresInGame(gameId);
      if(result == null || result.isEmpty()) {
        throw new RuntimeException("Nothing found");
      }
      return result;
    } catch (RuntimeException e) {
      log.error("Could not aggregate rounds for game with ID %s".formatted(gameId), e);
      throw e;
    }
  }

  public Map<UUID, AggregatedRoundScore> getAggregatedRoundScoresForRound(UUID roundId) {
    var foundScores = this.roundScoreRepository.getAggregatedRoundScoresForRound(roundId);

    // If somehow there are no scores, we could just create them - however this is not useful
    // since that most likely happens if we're in the middle of a synchronous call and therefore
    // would store incorrect scores...
    if(foundScores == null) {
      throw new RuntimeException("Scores for round with ID %s not found".formatted(roundId));
    }

    return foundScores;
  }

  public Map<UUID, AggregatedRoundScore> accumulateAndSaveRoundScoresForRound(UUID roundId) {
    var roundScoreBuilder = RoundScoreJpa.builder()
        .roundId(roundId);

    Map<UUID, AggregatedRoundScore> scores =
        this.roundScoreAggregator.aggregateRoundScoresForRound(roundId);

    this.roundScoreRepository.saveAggregatedRoundScoresForRound(roundId, scores);

    return scores;
  }
}

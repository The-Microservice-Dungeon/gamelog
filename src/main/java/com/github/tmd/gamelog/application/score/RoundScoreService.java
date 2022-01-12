package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.adapter.jpa.score.RoundScoreJpa;
import com.github.tmd.gamelog.adapter.jpa.score.RoundScoreRepository;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class RoundScoreService {
  private final RoundScoreAggregator roundScoreAggregator;
  private final RoundScoreRepository roundScoreRepository;


  public RoundScoreService(
      RoundScoreAggregator roundScoreAggregator,
      RoundScoreRepository roundScoreRepository) {
    this.roundScoreAggregator = roundScoreAggregator;
    this.roundScoreRepository = roundScoreRepository;
  }

  public void accumulateAndSaveRoundScoresForRound(UUID roundId) {
    var roundScoreBuilder = RoundScoreJpa.builder()
        .roundId(roundId);

    Map<UUID, AggregatedRoundScore> scores =
        this.roundScoreAggregator.accumulateAndSaveRoundScoresForRound(roundId);

    this.roundScoreRepository.saveAggregatedRoundScoresForRound(roundId, scores);
  }
}

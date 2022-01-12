package com.github.tmd.gamelog.adapter.jpa.score;

import com.github.tmd.gamelog.application.score.AggregatedRoundScore;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class RoundScoreRepository {
  private final RoundScoreJpaRepository roundScoreJpaRepository;
  private final RoundScoreMapper roundScoreMapper;

  public RoundScoreRepository(
      RoundScoreJpaRepository roundScoreJpaRepository,
      RoundScoreMapper roundScoreMapper) {
    this.roundScoreJpaRepository = roundScoreJpaRepository;
    this.roundScoreMapper = roundScoreMapper;
  }

  public void saveAggregatedRoundScoresForRound(UUID roundId,
      Map<UUID, AggregatedRoundScore> roundScores) {
    var toSave = RoundScoreJpa.builder()
        .roundId(roundId)
        .playerRoundScores(this.roundScoreMapper.toPersistenceMap(roundScores))
        .build();

    this.roundScoreJpaRepository.save(toSave);
  }
}

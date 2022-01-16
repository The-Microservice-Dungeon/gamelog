package com.github.tmd.gamelog.domain.score.repository;

import com.github.tmd.gamelog.domain.score.entity.AggregatedRoundScore;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AggregatedRoundScoreRepository {
  void saveAggregatedRoundScoresForRound(UUID roundId, Map<UUID, AggregatedRoundScore> roundScores);
  Map<UUID, List<AggregatedRoundScore>> findAllOrderedRoundScoresInGame(UUID gameId);
  Map<UUID, AggregatedRoundScore> getAggregatedRoundScoresForRound(UUID roundId);
}

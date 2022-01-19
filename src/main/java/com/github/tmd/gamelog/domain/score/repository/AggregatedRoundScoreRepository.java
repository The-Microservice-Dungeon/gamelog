package com.github.tmd.gamelog.domain.score.repository;

import com.github.tmd.gamelog.domain.score.vo.AggregatedRoundScore;
import java.util.List;
import java.util.Map;
import java.util.UUID;


// TODO: This might need to be removed
public interface AggregatedRoundScoreRepository {
  void saveAggregatedRoundScoresForRound(UUID roundId, Map<UUID, AggregatedRoundScore> roundScores);
  Map<UUID, List<AggregatedRoundScore>> findAllOrderedRoundScoresInGame(UUID gameId);
  Map<UUID, AggregatedRoundScore> getAggregatedRoundScoresForRound(UUID roundId);
}

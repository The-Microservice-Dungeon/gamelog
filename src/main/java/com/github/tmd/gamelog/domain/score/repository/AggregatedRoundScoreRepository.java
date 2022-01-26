package com.github.tmd.gamelog.domain.score.repository;

import com.github.tmd.gamelog.domain.score.vo.AggregatedScore;
import java.util.List;
import java.util.Map;
import java.util.UUID;


// TODO: This might need to be removed
public interface AggregatedRoundScoreRepository {
  void saveAggregatedRoundScoresForRound(UUID roundId, Map<UUID, AggregatedScore> roundScores);
  Map<UUID, List<AggregatedScore>> findAllOrderedRoundScoresInGame(UUID gameId);
  Map<UUID, AggregatedScore> getAggregatedRoundScoresForRound(UUID roundId);
}

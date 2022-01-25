package com.github.tmd.gamelog.adapter.jpa.score;

import com.github.tmd.gamelog.domain.score.vo.AggregatedRoundScore;
import com.github.tmd.gamelog.domain.score.repository.AggregatedRoundScoreRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class RoundScoreRepositoryImpl implements AggregatedRoundScoreRepository {
  private final RoundScoreJpaRepository roundScoreJpaRepository;
  private final RoundScoreMapper roundScoreMapper;

  public RoundScoreRepositoryImpl(
      RoundScoreJpaRepository roundScoreJpaRepository,
      RoundScoreMapper roundScoreMapper) {
    this.roundScoreJpaRepository = roundScoreJpaRepository;
    this.roundScoreMapper = roundScoreMapper;
  }

  @Transactional
  public void saveAggregatedRoundScoresForRound(UUID roundId,
      Map<UUID, AggregatedRoundScore> roundScores) {
    var toSave = RoundScoreJpa.builder()
        .roundId(roundId)
        .playerRoundScores(this.roundScoreMapper.toPersistenceMap(roundScores))
        .build();

    this.roundScoreJpaRepository.save(toSave);
  }

  @Transactional
  public Map<UUID, List<AggregatedRoundScore>> findAllOrderedRoundScoresInGame(UUID gameId) {
    var foundRoundScores = roundScoreJpaRepository.findAllOrderedRoundScoresInGame(gameId);
    Map<UUID, List<AggregatedRoundScore>> mapThingy = new HashMap<>();

    for(var roundScore : foundRoundScores) {
      for(var singleScore : roundScore.getPlayerRoundScores().entrySet()) {
        var list = mapThingy.getOrDefault(singleScore.getKey(), new ArrayList<>());
        list.add(this.roundScoreMapper.toAggregatedRoundScore(singleScore.getValue()));
        mapThingy.put(singleScore.getKey(), list);
      }
    }

    return mapThingy;
  }

  @Transactional
  public Map<UUID, AggregatedRoundScore> getAggregatedRoundScoresForRound(UUID roundId) {
    return this.roundScoreMapper.toAggregatedRoundScoreMap(this.roundScoreJpaRepository.findByRoundId(roundId).getPlayerRoundScores());
  }
}

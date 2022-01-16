package com.github.tmd.gamelog.application.score.accumulator;

import com.github.tmd.gamelog.application.history.RobotHistoryService;
import com.github.tmd.gamelog.domain.score.core.AbstractCategorizedRoundScoreAccumulator;
import com.github.tmd.gamelog.domain.score.vo.MiningRoundScore;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class MiningRoundScoreAccumulator extends
    AbstractCategorizedRoundScoreAccumulator<MiningRoundScore> {
  private final RobotHistoryService robotHistoryService;

  public MiningRoundScoreAccumulator(
      RobotHistoryService robotHistoryService) {
    this.robotHistoryService = robotHistoryService;
  }

  @Override
  public Map<UUID, MiningRoundScore> accumulateRoundScores(UUID roundId) {
    var minedResources = this.robotHistoryService.getMinedResourceInRound(roundId);

    var allPlayerIds = new HashSet<>(minedResources.keySet());

    var miningRoundScores = new HashMap<UUID, MiningRoundScore>();
    for(UUID id : allPlayerIds) {
      var mining = minedResources.getOrDefault(id, Collections.emptyList());
      miningRoundScores.put(id, new MiningRoundScore(mining));
    }

    return miningRoundScores;
  }
}

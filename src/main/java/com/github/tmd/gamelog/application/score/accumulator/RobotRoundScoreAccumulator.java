package com.github.tmd.gamelog.application.score.accumulator;

import com.github.tmd.gamelog.application.history.RobotHistoryService;
import com.github.tmd.gamelog.domain.score.core.AbstractCategorizedRoundScoreAccumulator;
import com.github.tmd.gamelog.domain.score.vo.RobotRoundScore;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class RobotRoundScoreAccumulator extends
    AbstractCategorizedRoundScoreAccumulator<RobotRoundScore> {
  private final RobotHistoryService robotHistoryService;

  public RobotRoundScoreAccumulator(
      RobotHistoryService robotHistoryService) {
    this.robotHistoryService = robotHistoryService;
  }

  @Override
  public Map<UUID, RobotRoundScore> accumulateRoundScores(UUID roundId) {
    var robotLevels = robotHistoryService.getRobotLevelsInRound(roundId);

    // Ugly but readable hack, since I don't know how to combine them in a fancy manner
    var allPlayerIds = new HashSet<>(robotLevels.keySet());

    var robotRoundScores = new HashMap<UUID, RobotRoundScore>();
    for(UUID id : allPlayerIds) {
      var robotLevelsThingies = robotLevels.getOrDefault(id, Collections.emptyList());
      robotRoundScores.put(id, new RobotRoundScore(robotLevelsThingies));
    }

    return robotRoundScores;
  }
}

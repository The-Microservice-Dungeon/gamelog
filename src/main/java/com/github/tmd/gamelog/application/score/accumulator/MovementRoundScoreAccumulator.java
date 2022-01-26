package com.github.tmd.gamelog.application.score.accumulator;

import com.github.tmd.gamelog.application.history.RobotHistoryService;
import com.github.tmd.gamelog.domain.score.core.AbstractCategorizedRoundScoreAccumulator;
import com.github.tmd.gamelog.domain.score.vo.MovementRoundScore;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovementRoundScoreAccumulator extends
    AbstractCategorizedRoundScoreAccumulator<MovementRoundScore> {
  private final RobotHistoryService robotHistoryService;

  public MovementRoundScoreAccumulator(
      RobotHistoryService robotHistoryService) {
    this.robotHistoryService = robotHistoryService;
  }

  @Override
  public Map<UUID, MovementRoundScore> accumulateRoundScores(UUID roundId) {
    log.debug("Accumulating round scores in round {}", roundId);
    var passedMovementDifficulty = robotHistoryService.getPassedMovementDifficultyInRound(roundId);

    // Ugly but readable hack, since I don't know how to combine them in a fancy manner
    var allPlayerIds = new HashSet<>(passedMovementDifficulty.keySet());

    var movementRoundScores = new HashMap<UUID, MovementRoundScore>();
    for(UUID id : allPlayerIds) {
      var passedMovement = passedMovementDifficulty.getOrDefault(id, 0);
      movementRoundScores.put(id, new MovementRoundScore(passedMovement));
    }

    if(log.isDebugEnabled()) {
      for(var entry : movementRoundScores.entrySet()) {
        log.debug("Accumulated score {} for player {}", entry.getValue(), entry.getKey());
      }
    }

    return movementRoundScores;
  }
}

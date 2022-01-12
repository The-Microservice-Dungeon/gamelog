package com.github.tmd.gamelog.application.score.accumulator;

import com.github.tmd.gamelog.application.history.RobotHistoryService;
import com.github.tmd.gamelog.application.score.core.AbstractCategorizedRoundScoreAccumulator;
import com.github.tmd.gamelog.application.score.FightingRoundScore;
import com.github.tmd.gamelog.domain.score.core.ScoreCategory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FightingRoundScoreAccumulator extends
    AbstractCategorizedRoundScoreAccumulator<FightingRoundScore> {
  private final RobotHistoryService robotHistoryService;

  @Autowired
  public FightingRoundScoreAccumulator(
      RobotHistoryService robotHistoryService) {
    this.robotHistoryService = robotHistoryService;
  }

  @Override
  public Map<UUID, FightingRoundScore> accumulateRoundScores(UUID roundId) {
    var damageInRound = robotHistoryService.getGivenDamageInRound(roundId);
    var killsInRound = robotHistoryService.getNumberOfKillsInRound(roundId);
    var victimsInRound = robotHistoryService.getNumberOfVictimsInRound(roundId);

    // Ugly but readable hack, since I don't know how to combine them in a fancy manner
    var allPlayerIds = new HashSet<>(damageInRound.keySet());
    allPlayerIds.addAll(killsInRound.keySet());
    allPlayerIds.addAll(victimsInRound.keySet());

    var fightingRoundScores = new HashMap<UUID, FightingRoundScore>();
    for(UUID id : allPlayerIds) {
      var damage = damageInRound.getOrDefault(id, 0);
      var kills = killsInRound.getOrDefault(id, 0);
      var victims = victimsInRound.getOrDefault(id, 0);
      fightingRoundScores.put(id, new FightingRoundScore(kills, victims, damage));
    }

    return fightingRoundScores;
  }
}

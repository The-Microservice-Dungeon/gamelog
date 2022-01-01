package com.github.tmd.gamelog.domain.scoreboard.model;

import java.util.Map;
import lombok.Data;

@Data
public class PlayerGameScore {
  private final Map<Round, RoundScore> roundScores;

  public void addRoundScore(Round round, RoundScore roundScore) {
    if(roundScores.containsKey(round) && roundScores.get(round) != null) {
      throw new RuntimeException("Cannot update existing round score");
    }
    roundScores.put(round, roundScore);
  }
}

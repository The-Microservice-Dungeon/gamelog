package com.github.tmd.gamelog.domain.scoreboard.model;

import java.util.Map;
import lombok.Data;

@Data
public class PlayerGameScore {
  private final Map<Round, RoundScore> roundScores;
}

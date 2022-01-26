package com.github.tmd.gamelog.domain.score.entity;

import com.github.tmd.gamelog.domain.Game;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.score.vo.AggregatedScore;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Scoreboard {
  private final Game game;
  private final Map<Player, AggregatedScore> gameScores;
  private final Map<Player, List<AggregatedScore>> roundScores;
}

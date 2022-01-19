package com.github.tmd.gamelog.domain.score.entity;

import com.github.tmd.gamelog.domain.Game;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.score.vo.AggregatedGameScore;
import com.github.tmd.gamelog.domain.score.vo.AggregatedRoundScore;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Scoreboard {
  private final Game game;
  private final Map<Player, AggregatedGameScore> gameScores;
  private final Map<Player, List<AggregatedRoundScore>> roundScores;
}

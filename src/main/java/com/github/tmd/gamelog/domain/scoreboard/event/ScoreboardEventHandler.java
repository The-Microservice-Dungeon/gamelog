package com.github.tmd.gamelog.domain.scoreboard.event;

import java.util.UUID;

public interface ScoreboardEventHandler {
  void onInitializeScoreboard(UUID gameId);
}

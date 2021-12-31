package com.github.tmd.gamelog.domain.scoreboard.event;

import com.github.tmd.gamelog.domain.scoreboard.service.InitializeScoreboardService;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScoreboardEventHandlerImpl implements ScoreboardEventHandler {
  private final InitializeScoreboardService initializeScoreboardService;

  public ScoreboardEventHandlerImpl(
      InitializeScoreboardService initializeScoreboardService) {
    this.initializeScoreboardService = initializeScoreboardService;
  }


  @Override
  public void onInitializeScoreboard(UUID gameId) {
    log.info("Receieved event to initialize scoreboard for event {}", gameId);
    var scoreboard = initializeScoreboardService.initializeScoreboard(gameId);
    log.info("Initialized scoreboard with id {}, value: {}", scoreboard.getScoreboardId().id(), scoreboard);
  }
}

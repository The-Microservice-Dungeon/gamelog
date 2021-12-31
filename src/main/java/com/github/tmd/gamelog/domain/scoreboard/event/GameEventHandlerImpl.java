package com.github.tmd.gamelog.domain.scoreboard.event;

import com.github.tmd.gamelog.domain.scoreboard.service.AddRoundToScoreboardService;
import com.github.tmd.gamelog.domain.scoreboard.service.InitializeScoreboardService;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GameEventHandlerImpl implements GameEventHandler {
  private final InitializeScoreboardService initializeScoreboardService;
  private final AddRoundToScoreboardService addRoundToScoreboardService;

  public GameEventHandlerImpl(
      InitializeScoreboardService initializeScoreboardService,
      AddRoundToScoreboardService addRoundToScoreboardService) {
    this.initializeScoreboardService = initializeScoreboardService;
    this.addRoundToScoreboardService = addRoundToScoreboardService;
  }


  @Override
  public void onCreateGame(UUID gameId) {
    log.info("Receieved event to initialize scoreboard for event {}", gameId);
    var scoreboard = initializeScoreboardService.initializeScoreboard(gameId);
    log.info("Initialized scoreboard with id {}, value: {}", scoreboard.getScoreboardId().id(), scoreboard);
  }

  @Override
  public void onStartRound(UUID gameId, UUID roundId, Integer roundNumber) {
    log.info("Receieved start round {} with id {} event in game {}", roundNumber, roundId, gameId);
    var scoreboard = addRoundToScoreboardService.addRoundToScoreboard(gameId, roundId, roundNumber);
    log.info("Added round to scoreboard, value: {}", scoreboard.getScoreboardId().id(), scoreboard);
  }

  @Override
  public void onEndRound(UUID gameId, UUID roundId) {
    log.info("Receieved end round with id {} event in game {}", roundId, gameId);

    // When the round ended perform synchronous calls, command resolution, ...
  }
}

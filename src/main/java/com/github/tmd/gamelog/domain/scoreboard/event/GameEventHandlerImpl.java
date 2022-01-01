package com.github.tmd.gamelog.domain.scoreboard.event;

import com.github.tmd.gamelog.domain.scoreboard.service.AddRoundToScoreboardService;
import com.github.tmd.gamelog.domain.scoreboard.service.AggregateRoundScoresService;
import com.github.tmd.gamelog.domain.scoreboard.service.InitializeScoreboardService;
import com.github.tmd.gamelog.domain.scoreboard.service.LockScoreboardService;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GameEventHandlerImpl implements GameEventHandler {
  private final InitializeScoreboardService initializeScoreboardService;
  private final AddRoundToScoreboardService addRoundToScoreboardService;
  private final LockScoreboardService lockScoreboardService;
  private final AggregateRoundScoresService roundScoreAggregator;

  public GameEventHandlerImpl(
      InitializeScoreboardService initializeScoreboardService,
      AddRoundToScoreboardService addRoundToScoreboardService,
      LockScoreboardService lockScoreboardService,
      AggregateRoundScoresService roundScoreAggregator) {
    this.initializeScoreboardService = initializeScoreboardService;
    this.addRoundToScoreboardService = addRoundToScoreboardService;
    this.lockScoreboardService = lockScoreboardService;
    this.roundScoreAggregator = roundScoreAggregator;
  }


  @Override
  public void onCreateGame(UUID gameId) {
    log.info("Receieved event to initialize scoreboard for event {}", gameId);
    var scoreboard = initializeScoreboardService.initializeScoreboard(gameId);
    log.info("Initialized scoreboard with id {}, value: {}", scoreboard.getScoreboardId().id(), scoreboard);
  }

  @Override
  public void onEndGame(UUID gameId) {
    log.info("Receieved end game with id {} event", gameId);
    var scoreboard = lockScoreboardService.lockScoreboard(gameId);
    log.info("Locked scoreboard with id {}, value: {}", scoreboard.getScoreboardId().id(), scoreboard);
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
    var scoreboard = this.roundScoreAggregator.aggregate(gameId, roundId);
    log.info("Aggregated round scores to scoreboard, value: {}", scoreboard);
  }
}

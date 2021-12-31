package com.github.tmd.gamelog.domain.game;

import com.github.tmd.gamelog.domain.player.PlayerService;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GameEventHandlerImpl implements GameEventHandler {
  private final GameService gameService;
  private final PlayerService playerService;

  @Autowired
  public GameEventHandlerImpl(GameService gameService,
      PlayerService playerService) {
    this.gameService = gameService;
    this.playerService = playerService;
  }

  @Override
  public void onGameCreate(UUID gameId, ZonedDateTime time) {
    log.info("Game with id {} created at {}", gameId, time);
    this.gameService.createNewGame(gameId);
  }

  @Override
  public void onGameStart(UUID gameId, ZonedDateTime time) {
    log.info("Game with id {} started at {}", gameId, time);
    this.gameService.startGame(gameId);
  }

  @Override
  public void onGameEnd(UUID gameId, ZonedDateTime time) {
    log.info("Game with id {} ended at {}", gameId, time);
    this.gameService.endGame(gameId);
  }

  @Override
  public void onRoundStart(UUID gameId, UUID roundId, Integer roundNumber, ZonedDateTime time) {
    log.info("New Round number {} with id {} started within game {} at {}", roundNumber, roundId, gameId, time);
    this.gameService.addRound(gameId, roundId, roundNumber);
  }
}

package com.github.tmd.gamelog.domain.game;

import java.time.ZonedDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GameEventHandlerImpl implements GameEventHandler {
  private final GameService gameService;

  @Autowired
  public GameEventHandlerImpl(GameService gameService) {
    this.gameService = gameService;
  }

  @Override
  public void onGameCreate(String gameId, ZonedDateTime time) {
    log.info("Game with id {} created at {}", gameId, time);
    this.gameService.createNewGame(gameId);
  }

  @Override
  public void onGameStart(String gameId, ZonedDateTime time) {
    log.info("Game with id {} started at {}", gameId, time);
    this.gameService.startGame(gameId);
  }

  @Override
  public void onGameEnd(String gameId, ZonedDateTime time) {
    log.info("Game with id {} ended at {}", gameId, time);
    this.gameService.endGame(gameId);
  }
}

package com.github.tmd.gamelog.domain.player;

import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PlayerEventHandlerImpl implements PlayerEventHandler {
  private final PlayerService playerService;

  public PlayerEventHandlerImpl(PlayerService playerService) {
    this.playerService = playerService;
  }

  @Override
  public void onPlayerRegister(UUID playerId, String userName, ZonedDateTime time) {
      log.info("Player {} with ID {} registered at {}", userName, playerId, time);
      this.playerService.registerPlayer(playerId, userName);
  }
}

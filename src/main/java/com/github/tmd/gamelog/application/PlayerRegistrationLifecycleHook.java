package com.github.tmd.gamelog.application;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.PlayerStatusChangedEvent;
import java.time.Instant;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class PlayerRegistrationLifecycleHook implements GameLifecycleHook {
  private final PlayerService playerService;

  public PlayerRegistrationLifecycleHook(
      PlayerService playerService) {
    this.playerService = playerService;
  }

  @Override
  @Transactional
  public void onPlayerStatus(PlayerStatusChangedEvent event, UUID gameId, Instant timestamp) {
    playerService.createOrUpdatePlayer(event.userId(), event.userName());
  }
}

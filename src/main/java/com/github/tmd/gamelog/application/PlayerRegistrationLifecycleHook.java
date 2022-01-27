package com.github.tmd.gamelog.application;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatus;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.PlayerStatusChangedEvent;
import com.github.tmd.gamelog.application.history.GameHistoryService;
import java.time.Instant;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@Slf4j
public class PlayerRegistrationLifecycleHook implements GameLifecycleHook {
  private final PlayerService playerService;
  private final GameHistoryService gameHistoryService;

  public PlayerRegistrationLifecycleHook(
      PlayerService playerService,
      GameHistoryService gameHistoryService) {
    this.playerService = playerService;
    this.gameHistoryService = gameHistoryService;
  }

  @Override
  @Transactional
  public void onPlayerStatus(PlayerStatusChangedEvent event, UUID gameId, Instant timestamp) {
    log.debug("Received PlayerStatusChanged Event: {}, Game: {}, At: {}", event, gameId, timestamp);
    playerService.createOrUpdatePlayer(event.userId(), event.userName());
  }

  @Override
  @Transactional
  public void onGameStatus(GameStatusEvent event, Instant timestamp) {
    log.debug("Received GameStatusEvent Event: {}, At: {}", event, timestamp);
    if(event.status() == GameStatus.STARTED) {
      this.playerService.findParticipatingPlayersInGame(event.gameId())
          .forEach(player -> {
            if(!this.gameHistoryService.playerHistoryExists(event.gameId(), player.getId())) {
              this.gameHistoryService.insertGamePlayerStatusHistory(event.gameId(), player.getId(),
                  player.getName(), Instant.now());
            }
            playerService.createOrUpdatePlayer(player);
          });



    }
  }
}

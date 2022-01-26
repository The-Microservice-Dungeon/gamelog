package com.github.tmd.gamelog.application.history;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.PlayerStatusChangedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatus;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.FightingEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.MiningEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.MovementEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.PlanetBlockedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentItemPriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentResourcePriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.TradingEvent;
import com.github.tmd.gamelog.application.GameLifecycleHook;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
@Slf4j
public class HistoryLifecycleHook implements GameLifecycleHook {
  private final GameHistoryService gameHistoryService;
  private final RobotHistoryService robotHistoryService;
  private final TradingHistoryService tradingHistoryService;

  public HistoryLifecycleHook(
      GameHistoryService gameHistoryService,
      RobotHistoryService robotHistoryService,
      TradingHistoryService tradingHistoryService) {
    this.gameHistoryService = gameHistoryService;
    this.robotHistoryService = robotHistoryService;
    this.tradingHistoryService = tradingHistoryService;
  }

  @Override
  @Transactional
  public void onGameStatus(GameStatusEvent event, Instant timestamp) {
    log.debug("Received GameStatus Event: {}, At: {}", event, timestamp);
    gameHistoryService.insertGameStatusHistory(event.gameId(), event.status(), timestamp);
  }

  @Override
  @Transactional
  public void onPlayerStatus(PlayerStatusChangedEvent event, UUID gameId, Instant timestamp) {
    log.debug("Received PlayerStatus Event: {}, Game: {}, At: {}", event, gameId, timestamp);
    gameHistoryService.insertGamePlayerStatusHistory(gameId, event.userId(), event.userName(), timestamp);
  }

  @Override
  @Transactional
  public void onRoundStatus(RoundStatusChangedEvent event, UUID gameId, Instant timestamp) {
    log.debug("Received RoundStatus Event: {}, Game: {}, At: {}", event, gameId, timestamp);
    var roundId = event.roundId();
    gameHistoryService.insertGameRoundStatusHistory(gameId, roundId, event.roundNumber(),
        event.roundStatus(), timestamp);

    // TODO: Well this could take a loooooong time
    // TODO: Multiple synchronous calls
    // TODO: Schedule the calls so that they could be called at a later point
    if(event.roundStatus() == RoundStatus.ENDED) {
      for (var player : gameHistoryService.getAllParticipatingPlayersInGame(gameId)) {
        this.robotHistoryService.insertRobotRoundHistoryForPlayer(roundId, player);
      }

      this.tradingHistoryService.insertBalanceHistory(roundId, event.roundNumber());
    }
  }

  @Override
  @Transactional
  public void onRobotMovement(MovementEvent event, UUID transactionId, Instant timestamp) {
    log.debug("Received RobotMovement Event: {}, Transaction: {}, At: {}", event, transactionId, timestamp);
    if (event.success()) {
      robotHistoryService.insertMovementHistory(transactionId, event.robots(), event.planet()
          .planetId(), event.planet().movementDifficulty(), timestamp);
    }
  }

  @Override
  @Transactional
  public void onRobotPlanetBlocked(PlanetBlockedEvent event, UUID transactionId,
      Instant timestamp) {
    log.debug("Received PlanetBlocked Event: {}, Transaction: {}, At: {}", event, transactionId, timestamp);
    if (event.success()) {
      robotHistoryService.insertPlanetBlockHistory(transactionId, event.planetId(), timestamp);
    }
  }

  @Override
  @Transactional
  public void onRobotMining(MiningEvent event, UUID transactionId, Instant timestamp) {
    log.debug("Received Mining Event: {}, Transaction: {}, At: {}", event, transactionId, timestamp);
    if (event.success()) {
      robotHistoryService.insertMiningHistory(transactionId, event.updateInventory(),
          event.resourceType(), timestamp);
    }
  }

  @Override
  @Transactional
  public void onRobotFighting(FightingEvent event, UUID transactionId, Instant timestamp) {
    log.debug("Received Fighting Event: {}, Transaction: {}, At: {}", event, transactionId, timestamp);
    if (event.success()) {
      robotHistoryService.insertFightHistory(transactionId, event.attacker(), event.defender(),
          event.remainingDefenderHealth(), timestamp);
    }
  }

  @Override
  @Transactional
  public void onTrade(TradingEvent event, UUID transactionId, Instant timestamp) {
    log.debug("Received Trading Event: {}, Transaction: {}, At: {}", event, transactionId, timestamp);
    if (event.success()) {
      this.tradingHistoryService.insertTradingHistory(transactionId, event.amount(), timestamp);
    }
  }
}

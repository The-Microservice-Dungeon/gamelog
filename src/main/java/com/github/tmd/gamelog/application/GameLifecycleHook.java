package com.github.tmd.gamelog.application;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.PlayerStatusChangedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.FightingEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.MiningEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.MovementEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.PlanetBlockedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentItemPriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentResourcePriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.TradingEvent;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public interface GameLifecycleHook {
  default void onGameStatus(GameStatusEvent event, Instant timestamp) {

  }

  default void onPlayerStatus(PlayerStatusChangedEvent event, UUID gameId, Instant timestamp) {

  }

  default void onRoundStatus(RoundStatusChangedEvent event, UUID gameId, Instant timestamp) {

  }

  default void onRobotMovement(MovementEvent movementEvent, UUID transactionId, Instant timestamp) {

  }

  default void onRobotPlanetBlocked(PlanetBlockedEvent event, UUID transactionId, Instant timestamp) {

  }

  default void onRobotMining(MiningEvent miningEvent, UUID transactionId, Instant timestamp) {

  }

  default void onRobotFighting(FightingEvent fightingEvent, UUID transactionId, Instant timestamp) {

  }

  default void onTrade(TradingEvent tradingEvent, UUID transactionId, Instant timestamp) {

  }

  default void onCurrentItemPricesAnnouncement(Set<CurrentItemPriceEvent> itemPrices, Instant timestamp) {

  }

  default void onCurrentResourcePricesAnnouncement(Set<CurrentResourcePriceEvent> resourcePrices, Instant timestamp) {

  }
}

package com.github.tmd.gamelog.application;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.PlayerStatusChangedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.FightingEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.MiningEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.robot.MovementEvent;
//import com.github.tmd.gamelog.adapter.event.gameEvent.robot.PlanetBlockedEvent;
//import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentItemPriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentResourcePriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.TradingEvent;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * Allows to create a hook into the gameplay events. The event listener will propagate all
 * events into every bean-registered hook. This decouples our features from the event listeners.
 * The Ordering is important, as Hooks with the lowest defined precedence will be used first.
 * Also, the exception handling within the hooks is crucial, as there is no global exeception
 * handler defined in the event listeners. Every exception needs to be taken care of.
 */
public interface GameLifecycleHook {
  default void onGameStatus(GameStatusEvent event, Instant timestamp) {

  }

  default void onPlayerStatus(PlayerStatusChangedEvent event, UUID gameId, Instant timestamp) {

  }

  default void onRoundStatus(RoundStatusChangedEvent event, UUID gameId, Instant timestamp) {

  }

  default void onRobotMovement(MovementEvent movementEvent, UUID transactionId, Instant timestamp) {

  }

  //default void onRobotPlanetBlocked(PlanetBlockedEvent event, UUID transactionId, Instant timestamp) {

  //}

  default void onRobotMining(MiningEvent miningEvent, UUID transactionId, Instant timestamp) {

  }

  default void onRobotFighting(FightingEvent fightingEvent, UUID transactionId, Instant timestamp) {

  }

  default void onTrade(TradingEvent tradingEvent, UUID transactionId, Instant timestamp) {

  }

 // default void onCurrentItemPricesAnnouncement(Set<CurrentItemPriceEvent> itemPrices, Instant timestamp) {

  //}

  default void onCurrentResourcePricesAnnouncement(Set<CurrentResourcePriceEvent> resourcePrices, Instant timestamp) {

  }
}

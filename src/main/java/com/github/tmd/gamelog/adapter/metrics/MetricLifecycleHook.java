package com.github.tmd.gamelog.adapter.metrics;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatus;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatus;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
//import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentItemPriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentResourcePriceEvent;
import com.github.tmd.gamelog.application.GameLifecycleHook;
import com.github.tmd.gamelog.application.score.service.GameScoreService;
import com.github.tmd.gamelog.application.score.service.RoundScoreService;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Hook to provide some interesting metrics. Has the lowest precedence as it might depend on other
 * hooks (like scores)
 */
@Component
@Order(value = Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class MetricLifecycleHook implements GameLifecycleHook {

  private final MetricService metricService;
  private final RoundScoreService roundScoreService;
  private final GameScoreService gameScoreService;

  public MetricLifecycleHook(MetricService metricService,
      RoundScoreService roundScoreService,
      GameScoreService gameScoreService) {
    this.metricService = metricService;
    this.roundScoreService = roundScoreService;
    this.gameScoreService = gameScoreService;
  }

  @Override
  public void onGameStatus(GameStatusEvent event, Instant timestamp) {
    if(event.status() == GameStatus.STARTED) {
      this.metricService.reset();
      this.metricService.init();
    }
  }

  @Override
  public void onRoundStatus(RoundStatusChangedEvent event, UUID gameId, Instant timestamp) {
    log.trace("Received RoundStatus Event: {}, Game: {}, At: {}", event, gameId, timestamp);

    try {
      this.metricService.publishRoundStatus(event.roundStatus().ordinal());
      this.metricService.publishRoundNumber(event.roundNumber());
    } catch (Exception e) {
      // Pokémon catch
      log.error("Couldn't publish metric", e);
    }

    if (event.roundStatus() == RoundStatus.ENDED) {
      // We assume that the game scores are already calculated, which should happen, since the
      // RoundScoreLifecycle Hook has a higher precedence than the Metric Lifecycle Hook.
      var gameScores = gameScoreService.getScoresInGame(gameId);

      for (var entry : gameScores.entrySet()) {
        var playerName = entry.getKey().getName();
        var totalScore = entry.getValue().score();
        var tradingScore = entry.getValue().getTradingScore();
        var fightingScore = entry.getValue().getFightingScore();
        var miningScore = entry.getValue().getMiningScore();
        var movementScore = entry.getValue().getMovementScore();
        var robotScore = entry.getValue().getRobotScore();

        try {
          metricService.publishScores(playerName, totalScore, tradingScore, fightingScore,
              miningScore, movementScore, robotScore);
        } catch (Exception e) {
          // Pokémon catch
          log.error("Couldn't publish metric", e);
        }
      }
    }
  }

 /* @Override
  public void onCurrentItemPricesAnnouncement(Set<CurrentItemPriceEvent> itemPrices,
      Instant timestamp) {
    log.trace("Received CurrentItemPrice Event: {}, At: {}", itemPrices, timestamp);
    for (var announcement : itemPrices) {
      try {
        this.metricService.publishItemPrice(announcement.name(), announcement.price());
      } catch (Exception e) {
        // Pokémon catch
        log.error("Couldn't publish metric", e);
      }
    }
  }
*/
  @Override
  public void onCurrentResourcePricesAnnouncement(Set<CurrentResourcePriceEvent> resourcePrices,
      Instant timestamp) {
    log.trace("Received CurrentResourcePrice Event: {}, At: {}", resourcePrices, timestamp);
    for (var announcement : resourcePrices) {
      try {
        this.metricService.publishResourcePrice(announcement.name(), announcement.price());
      } catch (Exception e) {
        // Pokémon catch
        log.error("Couldn't publish metric", e);
      }
    }
  }
}

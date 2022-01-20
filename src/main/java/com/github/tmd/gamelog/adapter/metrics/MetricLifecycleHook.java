package com.github.tmd.gamelog.adapter.metrics;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatus;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentItemPriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentResourcePriceEvent;
import com.github.tmd.gamelog.application.GameLifecycleHook;
import com.github.tmd.gamelog.application.score.service.RoundScoreService;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class MetricLifecycleHook implements GameLifecycleHook {

  private final MetricService metricService;
  private final RoundScoreService roundScoreService;

  public MetricLifecycleHook(MetricService metricService,
      RoundScoreService roundScoreService) {
    this.metricService = metricService;
    this.roundScoreService = roundScoreService;
  }

  @Override
  public void onGameStatus(GameStatusEvent event, Instant timestamp) {
    this.metricService.publishGameStatus(event.status().ordinal(), event.status().toString());
  }

  @Override
  public void onRoundStatus(RoundStatusChangedEvent event, UUID gameId, Instant timestamp) {
    this.metricService.publishRoundNumber(event.roundNumber());

    if (event.roundStatus() == RoundStatus.ENDED) {
      // We assume that the round scores are already calculated, which should happen, since the
      // RoundScoreLifecycle Hook has a higher precedence than the Metric Lifecycle Hook.
      var roundScores = roundScoreService.getAggregatedRoundScoresForRound(event.roundId());

      for (var entry : roundScores.entrySet()) {
        // TODO: use the playerName instead of the UUID -> Probably refactoring of the
        //  aggregation to use a strong player reference.
        var playerName = entry.getKey().toString();
        var totalScore = entry.getValue().score();
        var tradingScore = entry.getValue().getTradingScore();
        var fightingScore = entry.getValue().getFightingScore();
        var miningScore = entry.getValue().getMiningScore();
        var movementScore = entry.getValue().getMovementScore();
        var robotScore = entry.getValue().getRobotScore();

        metricService.publishScores(playerName, totalScore, tradingScore, fightingScore,
            miningScore, movementScore, robotScore);
      }
    }
  }

  @Override
  public void onCurrentItemPricesAnnouncement(Set<CurrentItemPriceEvent> itemPrices,
      Instant timestamp) {
    for (var announcement : itemPrices) {
      this.metricService.publishItemPrice(announcement.name(), announcement.price());
    }
  }

  @Override
  public void onCurrentResourcePricesAnnouncement(Set<CurrentResourcePriceEvent> resourcePrices,
      Instant timestamp) {
    for (var announcement : resourcePrices) {
      this.metricService.publishResourcePrice(announcement.name(), announcement.price());
    }
  }
}

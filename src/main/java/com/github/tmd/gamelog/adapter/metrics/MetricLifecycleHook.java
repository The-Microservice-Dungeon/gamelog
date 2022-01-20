package com.github.tmd.gamelog.adapter.metrics;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentItemPriceEvent;
import com.github.tmd.gamelog.adapter.event.gameEvent.trading.CurrentResourcePriceEvent;
import com.github.tmd.gamelog.application.GameLifecycleHook;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class MetricLifecycleHook implements GameLifecycleHook {
  private final MetricService metricService;

  public MetricLifecycleHook(MetricService metricService) {
    this.metricService = metricService;
  }

  @Override
  public void onGameStatus(GameStatusEvent event, Instant timestamp) {
    this.metricService.publishGameStatus(event.status().ordinal(), event.status().toString());
  }

  @Override
  public void onRoundStatus(RoundStatusChangedEvent event, UUID gameId, Instant timestamp) {
    this.metricService.publishRoundNumber(event.roundNumber());
  }

  @Override
  public void onCurrentItemPricesAnnouncement(Set<CurrentItemPriceEvent> itemPrices,
      Instant timestamp) {
    for(var announcement : itemPrices) {
      this.metricService.publishItemPrice(announcement.name(), announcement.price());
    }
  }

  @Override
  public void onCurrentResourcePricesAnnouncement(Set<CurrentResourcePriceEvent> resourcePrices,
      Instant timestamp) {
    for(var announcement : resourcePrices) {
      this.metricService.publishResourcePrice(announcement.name(), announcement.price());
    }
  }
}

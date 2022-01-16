package com.github.tmd.gamelog.adapter.metrics;

import io.micrometer.core.instrument.Meter.Id;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;

// TODO: Provide score metrics. We need to publish the aggregated score as a metric, otherwise
//  our data will not be consistent.

@Service
public class MetricService {
  private final MeterRegistry meterRegistry;

  public MetricService(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  public void reset() {
    // We will remove the meters for whatever reason... We're abusing meters for purposes they're
    // not meant for since they hold the state of the game. When a game starts we must remove the
    // previously populated meters in order to start with a clean state for the next game.
    // TODO: There's probably a better approach..
    meterRegistry.getMeters()
        .stream().filter(m -> m.getId().getName().startsWith("tmd"))
        .forEach(meterRegistry::remove);
    meterRegistry.clear();
  }

  public void publishGameStatus(String gameId, String status) {
    // We're setting the round status as a plain string label, while maintaining a constant gauge
    // this is a hack to be able to query round status. Also used in the other round status
    meterRegistry.gauge("tmd.game.status", Tags.of(Tag.of("game.id", gameId),
        Tag.of("status", status)), 1);
  }

  public void publishPlayerStatus(String gameId, String status) {
    meterRegistry.counter("tmd.game.players", Tags.of(Tag.of("game.id", gameId),
        Tag.of("status", status))).increment();
  }

  public void publishRoundNumber(String gameId, Integer roundNumber) {
    meterRegistry.gauge("tmd.game.round", Tags.of(Tag.of("game.id", gameId)),
        roundNumber.intValue());
  }

  public void publishRoundStatus(String gameId, String status) {
    // We're setting the round status as a plain string label, while maintaining a constant gauge
    // this is a hack to be able to query round status. Also used in the other round status
    meterRegistry.gauge("tmd.game.round.status",
        Tags.of(Tag.of("game.id", gameId), Tag.of("status", status)), 1);
  }

  public void publishItemPrice(String itemName, Integer value) {
    meterRegistry.gauge("tmd.trading.current.item.price.%s".formatted(itemName), value.intValue());
    meterRegistry.gauge("tmd.trading.current.item.price", Tags.of(Tag.of("name", itemName)), value.intValue());
  }

  public void publishResourcePrice(String resourceName, Integer value) {
    meterRegistry.gauge("tmd.trading.current.resource.price.%s".formatted(resourceName), value.intValue());
    meterRegistry.gauge("tmd.trading.current.resource.price", Tags.of(Tag.of("name", resourceName)), value.intValue());
  }

  public void publishTrade() {
    meterRegistry.counter("tmd.trading.trades.count").increment();
  }
}

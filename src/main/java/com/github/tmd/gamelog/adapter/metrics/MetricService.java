package com.github.tmd.gamelog.adapter.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class MetricService {
  private final MeterRegistry meterRegistry;

  public MetricService(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
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
}

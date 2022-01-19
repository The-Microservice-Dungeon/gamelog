package com.github.tmd.gamelog.adapter.metrics;

import io.micrometer.core.instrument.Meter.Id;
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
    this.initMetrics();
  }

  // Just for testing and Grafana setup
  private void initMetrics() {
    this.meterRegistry.gauge(DungeonMetrics.ROUND_GAUGE, 0);
    this.meterRegistry.gauge(DungeonMetrics.GAME_STATUS_INFO,
        Tags.of(Tag.of("status", "UNKNOWN")), -1);
  }

  public void publishRoundNumber(Integer roundNumber) {
    this.meterRegistry.gauge(DungeonMetrics.ROUND_GAUGE, roundNumber);
  }

  public void publishGameStatus(Integer value, String status) {
    this.meterRegistry.gauge(DungeonMetrics.GAME_STATUS_INFO,
        Tags.of(Tag.of("status", status)), value);
  }
}

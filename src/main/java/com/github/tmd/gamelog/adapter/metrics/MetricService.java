package com.github.tmd.gamelog.adapter.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
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
    meterRegistry.gauge("tmd_game_status", Set.of(Tag.of("game_id", gameId),
        Tag.of("status", status)), 1);
  }

  public void publishPlayerStatus(String gameId, String status) {
    meterRegistry.counter("tmd_game_players", Set.of(Tag.of("game_id", gameId),
        Tag.of("status", status))).increment();
  }

  public void publishRoundNumber(String gameId, Integer roundNumber) {
    meterRegistry.gauge("tmd_game_round", Set.of(Tag.of("game_id", gameId)),
        roundNumber);
  }

  public void publishRoundStatus(String gameId, String status) {
    // We're setting the round status as a plain string label, while maintaining a constant gauge
    // this is a hack to be able to query round status. Also used in the other round status
    meterRegistry.gauge("tmd_game_round_status",
        Set.of(Tag.of("game_id", gameId), Tag.of("status", status)), 1);
  }
}

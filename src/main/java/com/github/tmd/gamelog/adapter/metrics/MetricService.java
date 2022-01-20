package com.github.tmd.gamelog.adapter.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter.Id;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.kafka.common.protocol.types.Field.Str;
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
    this.publishRoundNumber(0);
    this.publishGameStatus(-1, "UNKNOWN");
    this.publishItemPrice("rocket", 100);
    this.publishItemPrice("robot", 120);
    this.publishItemPrice("xxx", 50);

    this.publishResourcePrice("COAL", 10);
    this.publishResourcePrice("IRON", 20);
    this.publishResourcePrice("GEM", 30);

    this.publishScores("Player 1", 120.0, 10.0, 11.0, 12.0, 13.0, 14.0);
    this.publishScores("Player 2", 140.0, 15.0, 20.0, 22.0, 23.0, 24.0);
  }

  public void publishRoundNumber(int roundNumber) {
    buildStrongGauge(DungeonMetrics.ROUND_GAUGE, Tags.empty(), roundNumber);
  }

  public void publishGameStatus(int value, String status) {
    buildStrongGauge(DungeonMetrics.GAME_STATUS_INFO, Tags.of(Tag.of("status", status)), value);
  }

  public void publishItemPrice(String itemName, int value) {
    buildStrongGauge(DungeonMetrics.TRADING_ITEM_PRICES, Tags.of("name", itemName), value);
  }

  public void publishResourcePrice(String resourceName, int value) {
    buildStrongGauge(DungeonMetrics.TRADING_RESOURCE_PRICES, Tags.of("name", resourceName), value);
  }

  public void publishScores(String playerName, double totalScore, double tradingScore,
      double fightingScore, double miningScore, double movementScore, double robotScore) {
    this.publishTotalScore(playerName, totalScore);
    this.publishTradingScore(playerName, tradingScore);
    this.publishFightingScore(playerName, fightingScore);
    this.publishMiningScore(playerName, miningScore);
    this.publishMovementScore(playerName, movementScore);
    this.publishRobotScore(playerName, robotScore);
  }

  private void publishTotalScore(String playerName, double score) {
    buildStrongGauge(DungeonMetrics.SCORE_TOTAL, Tags.of("player.name", playerName), score);
  }

  private void publishTradingScore(String playerName, double score) {
    buildStrongGauge(DungeonMetrics.SCORE_TRADING, Tags.of("player.name", playerName), score);
  }

  private void publishFightingScore(String playerName, double score) {
    buildStrongGauge(DungeonMetrics.SCORE_FIGHTING, Tags.of("player.name", playerName), score);
  }

  private void publishMiningScore(String playerName, double score) {
    buildStrongGauge(DungeonMetrics.SCORE_MINING, Tags.of("player.name", playerName), score);
  }

  private void publishMovementScore(String playerName, double score) {
    buildStrongGauge(DungeonMetrics.SCORE_MOVEMENT, Tags.of("player.name", playerName), score);
  }

  private void publishRobotScore(String playerName, double score) {
    buildStrongGauge(DungeonMetrics.SCORE_ROBOT, Tags.of("player.name", playerName), score);
  }

  // We need strong references to maintain the metrics.
  // We're here totally misusing meters for displaying game-related stuff, and in a normal this is
  // meter use-case this would not be necessary. However, we're hacking our way around to provide
  // strong references by utilizing the Gauge Builder rather than using the extension method
  // on the meter registry which does not have an option to set strong references
  private Gauge buildStrongGauge(String name, Tags tags, double val) {
    return Gauge.builder(name, val, value1 -> value1.doubleValue())
        .tags(tags)
        .strongReference(true)
        .register(meterRegistry);
  }
}

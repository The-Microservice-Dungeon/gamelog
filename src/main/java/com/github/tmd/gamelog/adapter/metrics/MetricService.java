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

  public void publishRoundNumber(Integer roundNumber) {
    this.meterRegistry.gauge(DungeonMetrics.ROUND_GAUGE, roundNumber);
  }

  public void publishGameStatus(Integer value, String status) {
    this.meterRegistry.gauge(DungeonMetrics.GAME_STATUS_INFO,
        Tags.of(Tag.of("status", status)), value);
  }

  public void publishItemPrice(String itemName, Integer value) {
    this.meterRegistry.gauge(DungeonMetrics.TRADING_ITEM_PRICES,
        Tags.of(Tag.of("name", itemName)), value);
  }

  public void publishResourcePrice(String resourceName, Integer value) {
    this.meterRegistry.gauge(DungeonMetrics.TRADING_RESOURCE_PRICES,
        Tags.of(Tag.of("name", resourceName)), value);
  }

  public void publishScores(String playerName, Double totalScore, Double tradingScore,
      Double fightingScore, Double miningScore, Double movementScore, Double robotScore) {
    this.publishTotalScore(playerName, totalScore);
    this.publishTradingScore(playerName, tradingScore);
    this.publishFightingScore(playerName, fightingScore);
    this.publishMiningScore(playerName, miningScore);
    this.publishMovementScore(playerName, movementScore);
    this.publishRobotScore(playerName, robotScore);
  }

  private void publishTotalScore(String playerName, Double score) {
    this.meterRegistry.gauge(DungeonMetrics.SCORE_TOTAL,
        Tags.of(Tag.of("player.name", playerName)), score);
  }

  private void publishTradingScore(String playerName, Double score) {
    this.meterRegistry.gauge(DungeonMetrics.SCORE_TRADING,
        Tags.of(Tag.of("player.name", playerName)), score);
  }

  private void publishFightingScore(String playerName, Double score) {
    this.meterRegistry.gauge(DungeonMetrics.SCORE_FIGHTING,
        Tags.of(Tag.of("player.name", playerName)), score);
  }

  private void publishMiningScore(String playerName, Double score) {
    this.meterRegistry.gauge(DungeonMetrics.SCORE_MINING,
        Tags.of(Tag.of("player.name", playerName)), score);
  }

  private void publishMovementScore(String playerName, Double score) {
    this.meterRegistry.gauge(DungeonMetrics.SCORE_MOVEMENT,
        Tags.of(Tag.of("player.name", playerName)), score);
  }

  private void publishRobotScore(String playerName, Double score) {
    this.meterRegistry.gauge(DungeonMetrics.SCORE_ROBOT,
        Tags.of(Tag.of("player.name", playerName)), score);
  }
}

package com.github.tmd.gamelog.adapter.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Service;

@Service
public class MetricService {

  private final MeterRegistry meterRegistry;
  private final AtomicInteger atomicRound = new AtomicInteger();
  private final AtomicInteger atomicGame = new AtomicInteger();
  private final Map<String, AtomicInteger> atomicItemPrices = new HashMap<>();
  private final Map<String, AtomicInteger> atomicResourcePrices = new HashMap<>();
  private final Map<String, AtomicReference<Double>> atomicTotalScores = new HashMap<>();
  private final Map<String, AtomicReference<Double>> atomicTradingScores = new HashMap<>();
  private final Map<String, AtomicReference<Double>> atomicFightingScores = new HashMap<>();
  private final Map<String, AtomicReference<Double>> atomicMiningScores = new HashMap<>();
  private final Map<String, AtomicReference<Double>> atomicMovementScores = new HashMap<>();
  private final Map<String, AtomicReference<Double>> atomicRobotScores = new HashMap<>();

  public MetricService(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
    // this.initMetrics();
  }

  // Just for testing and Grafana setup
  private void initMetrics() {
    this.publishRoundNumber(0);
    this.publishGameStatus(-1);
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
    this.atomicRound.set(roundNumber);
    buildStrongGauge(DungeonMetrics.ROUND_GAUGE, Tags.empty(), this.atomicRound.get());
  }

  public void publishGameStatus(int value) {
    this.atomicGame.set(value);
    buildStrongGauge(DungeonMetrics.GAME_STATUS_INFO, Tags.empty(), this.atomicGame.get());
  }

  public void publishItemPrice(String itemName, int value) {
    this.atomicItemPrices.put(itemName, new AtomicInteger(value));
    buildStrongGauge(DungeonMetrics.TRADING_ITEM_PRICES, Tags.of("name", itemName),
        this.atomicItemPrices.get(itemName).get());
  }

  public void publishResourcePrice(String resourceName, int value) {
    this.atomicResourcePrices.put(resourceName, new AtomicInteger(value));
    buildStrongGauge(DungeonMetrics.TRADING_RESOURCE_PRICES, Tags.of("name", resourceName),
        this.atomicResourcePrices.get(resourceName).get());
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
    this.atomicTotalScores.put(playerName, new AtomicReference<>(score));
    buildStrongGauge(DungeonMetrics.SCORE_TOTAL, Tags.of("player.name", playerName),
        this.atomicTotalScores.get(playerName).get());
  }

  private void publishTradingScore(String playerName, double score) {
    this.atomicTradingScores.put(playerName, new AtomicReference<>(score));
    buildStrongGauge(DungeonMetrics.SCORE_TRADING, Tags.of("player.name", playerName),
        this.atomicTradingScores.get(playerName).get());
  }

  private void publishFightingScore(String playerName, double score) {
    this.atomicFightingScores.put(playerName, new AtomicReference<>(score));
    buildStrongGauge(DungeonMetrics.SCORE_FIGHTING, Tags.of("player.name", playerName),
        this.atomicFightingScores.get(playerName).get());
  }

  private void publishMiningScore(String playerName, double score) {
    this.atomicMiningScores.put(playerName, new AtomicReference<>(score));
    buildStrongGauge(DungeonMetrics.SCORE_MINING, Tags.of("player.name", playerName),
        this.atomicMiningScores.get(playerName).get());
  }

  private void publishMovementScore(String playerName, double score) {
    this.atomicMovementScores.put(playerName, new AtomicReference<>(score));
    buildStrongGauge(DungeonMetrics.SCORE_MOVEMENT, Tags.of("player.name", playerName),
        this.atomicMovementScores.get(playerName).get());
  }

  private void publishRobotScore(String playerName, double score) {
    this.atomicRobotScores.put(playerName, new AtomicReference<>(score));
    buildStrongGauge(DungeonMetrics.SCORE_ROBOT, Tags.of("player.name", playerName),
        this.atomicRobotScores.get(playerName).get());
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

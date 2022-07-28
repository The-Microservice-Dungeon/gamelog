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
  private final AtomicInteger atomicRoundStatus = new AtomicInteger();
 // private final Map<String, AtomicInteger> atomicItemPrices = new HashMap<>();
  private final Map<String, AtomicInteger> atomicResourcePrices = new HashMap<>();
  private final Map<String, AtomicReference<Double>> atomicTotalScores = new HashMap<>();
  private final Map<String, AtomicReference<Double>> atomicTradingScores = new HashMap<>();
  private final Map<String, AtomicReference<Double>> atomicFightingScores = new HashMap<>();
  private final Map<String, AtomicReference<Double>> atomicMiningScores = new HashMap<>();
  private final Map<String, AtomicReference<Double>> atomicMovementScores = new HashMap<>();
  private final Map<String, AtomicReference<Double>> atomicRobotScores = new HashMap<>();

  public MetricService(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
    this.init();
  }

  public void reset() {
    this.meterRegistry.find(DungeonMetrics.DUNGEON_PREFIX)
        .meters()
        .forEach(meter -> this.meterRegistry.remove(meter));
  }

  public void init() {
    this.meterRegistry.gauge(DungeonMetrics.ROUND_GAUGE, Tags.empty(), this.atomicRound);
    this.meterRegistry.gauge(DungeonMetrics.ROUND_STATUS_INFO, Tags.empty(), this.atomicRoundStatus);
  }

  public void publishRoundNumber(int roundNumber) {
    this.atomicRound.set(roundNumber);
  }

  public void publishRoundStatus(int value) {
    this.atomicRoundStatus.set(value);
  }

 /* public void publishItemPrice(String itemName, int value) {
    var atomicRef = this.atomicItemPrices.getOrDefault(itemName, new AtomicInteger());
    atomicRef.set(value);
    this.atomicItemPrices.put(itemName, atomicRef);

    var scoreMetric = DungeonMetrics.TRADING_ITEM_PRICES;
    var tags = Tags.of("name", itemName);

    var found = this.meterRegistry.find(scoreMetric).tags(tags).gauge();
    if(found == null) {
      this.meterRegistry.gauge(scoreMetric, tags, this.atomicItemPrices.get(itemName), ref -> ref.get());
    }
  }
*/
  public void publishResourcePrice(String resourceName, int value) {
    var atomicRef = this.atomicResourcePrices.getOrDefault(resourceName, new AtomicInteger());
    atomicRef.set(value);
    this.atomicResourcePrices.put(resourceName, atomicRef);

    var scoreMetric = DungeonMetrics.TRADING_RESOURCE_PRICES;
    var tags = Tags.of("name", resourceName);

    var found = this.meterRegistry.find(scoreMetric).tags(tags).gauge();
    if(found == null) {
      this.meterRegistry.gauge(scoreMetric, tags, this.atomicResourcePrices.get(resourceName), ref -> ref.get());
    }
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

  private void createScoreGaugeIfNotExists(String name, Tags tags, AtomicReference<Double> scoreRef) {
  }

  private void publishTotalScore(String playerName, double score) {
    var atomicRef = this.atomicTotalScores.getOrDefault(playerName, new AtomicReference<>());
    atomicRef.set(score);
    this.atomicTotalScores.put(playerName, atomicRef);

    var scoreMetric = DungeonMetrics.SCORE_TOTAL;
    var tags = Tags.of("player.name", playerName);

    var found = this.meterRegistry.find(scoreMetric).tags(tags).gauge();
    if(found == null) {
      this.meterRegistry.gauge(scoreMetric, tags, this.atomicTotalScores.get(playerName), ref -> ref.get());
    }
  }

  private void publishTradingScore(String playerName, double score) {
    var atomicRef = this.atomicTradingScores.getOrDefault(playerName, new AtomicReference<>());
    atomicRef.set(score);
    this.atomicTradingScores.put(playerName, atomicRef);

    var scoreMetric = DungeonMetrics.SCORE_TRADING;
    var tags = Tags.of("player.name", playerName);

    var found = this.meterRegistry.find(scoreMetric).tags(tags).gauge();
    if(found == null) {
      this.meterRegistry.gauge(scoreMetric, tags, this.atomicTradingScores.get(playerName), ref -> ref.get());
    }
  }

  private void publishFightingScore(String playerName, double score) {
    var atomicRef = this.atomicFightingScores.getOrDefault(playerName, new AtomicReference<>());
    atomicRef.set(score);
    this.atomicFightingScores.put(playerName, atomicRef);

    var scoreMetric = DungeonMetrics.SCORE_FIGHTING;
    var tags = Tags.of("player.name", playerName);

    var found = this.meterRegistry.find(scoreMetric).tags(tags).gauge();
    if(found == null) {
      this.meterRegistry.gauge(scoreMetric, tags, this.atomicFightingScores.get(playerName), ref -> ref.get());
    }
  }

  private void publishMiningScore(String playerName, double score) {
    var atomicRef = this.atomicMiningScores.getOrDefault(playerName, new AtomicReference<>());
    atomicRef.set(score);
    this.atomicMiningScores.put(playerName, atomicRef);

    var scoreMetric = DungeonMetrics.SCORE_MINING;
    var tags = Tags.of("player.name", playerName);

    var found = this.meterRegistry.find(scoreMetric).tags(tags).gauge();
    if(found == null) {
      this.meterRegistry.gauge(scoreMetric, tags, this.atomicMiningScores.get(playerName), ref -> ref.get());
    }
  }

  private void publishMovementScore(String playerName, double score) {
    var atomicRef = this.atomicMovementScores.getOrDefault(playerName, new AtomicReference<>());
    atomicRef.set(score);
    this.atomicMovementScores.put(playerName, atomicRef);

    var scoreMetric = DungeonMetrics.SCORE_MOVEMENT;
    var tags = Tags.of("player.name", playerName);

    var found = this.meterRegistry.find(scoreMetric).tags(tags).gauge();
    if(found == null) {
      this.meterRegistry.gauge(scoreMetric, tags, this.atomicMovementScores.get(playerName), ref -> ref.get());
    }
  }

  private void publishRobotScore(String playerName, double score) {
    var atomicRef = this.atomicRobotScores.getOrDefault(playerName, new AtomicReference<>());
    atomicRef.set(score);
    this.atomicRobotScores.put(playerName, atomicRef);

    var scoreMetric = DungeonMetrics.SCORE_ROBOT;
    var tags = Tags.of("player.name", playerName);

    var found = this.meterRegistry.find(scoreMetric).tags(tags).gauge();
    if(found == null) {
      this.meterRegistry.gauge(scoreMetric, tags, this.atomicRobotScores.get(playerName), ref -> ref.get());
    }
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

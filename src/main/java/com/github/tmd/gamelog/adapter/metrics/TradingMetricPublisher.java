package com.github.tmd.gamelog.adapter.metrics;

import com.github.tmd.gamelog.adapter.rest.client.TradingRestClient;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TradingMetricPublisher {

  private final TradingRestClient tradingRestClient;
  private final MeterRegistry meterRegistry;

  public TradingMetricPublisher(
      TradingRestClient tradingRestClient,
      MeterRegistry meterRegistry) {
    this.tradingRestClient = tradingRestClient;
    this.meterRegistry = meterRegistry;
  }

  @Async
  @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 15)
  public void publishItemPriceHistory() {
    try {
      var result = tradingRestClient.getItemPriceHistory();

      for (var entry : result) {
        var item = entry.name();
        for (var round : entry.history().entrySet()) {
          meterRegistry.gauge("tmd_trading_item_price",
              Set.of(Tag.of("round_number", round.getKey()), Tag.of("item_name", item)),
              round.getValue());
          meterRegistry.gauge("tmd_trading_item_price_%s".formatted(item),
              Set.of(Tag.of("round_number", round.getKey())), round.getValue());
        }
      }
    } catch (RuntimeException e) {
      log.error("Could not fetch price history", e);
    }
  }

  @Async
  @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 15)
  public void publishResourcePriceHistory() {
    try {
      var result = tradingRestClient.getResourcePriceHistory();

      for (var entry : result) {
        var resource = entry.name();
        for (var round : entry.history().entrySet()) {
          meterRegistry.gauge("tmd_trading_resource_price",
              Set.of(Tag.of("round_number", round.getKey()), Tag.of("resource_name", resource)),
              round.getValue());
          meterRegistry.gauge("tmd_trading_resource_price_%s".formatted(resource),
              Set.of(Tag.of("round_number", round.getKey())), round.getValue());
        }
      }
    } catch (RuntimeException e) {
      log.error("Could not fetch price history", e);
    }
  }
}

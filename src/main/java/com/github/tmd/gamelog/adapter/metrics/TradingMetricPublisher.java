package com.github.tmd.gamelog.adapter.metrics;

import com.github.tmd.gamelog.adapter.rest.client.TradingRestClient;
import com.github.tmd.gamelog.adapter.rest.client.response.ItemPriceHistoryEntryDto;
import com.github.tmd.gamelog.adapter.rest.client.response.ResourcePriceHistoryEntryDto;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import java.util.ArrayList;
import java.util.List;
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

  // We need to define them here to hold a strong reference from our metric
  private List<ItemPriceHistoryEntryDto> itemPriceHistory = new ArrayList<>();
  private List<ResourcePriceHistoryEntryDto> resourcePriceHistory = new ArrayList<>();

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
      itemPriceHistory = tradingRestClient.getItemPriceHistory();

      for (var entry : itemPriceHistory) {
        var item = entry.name();
        for (var round : entry.history().entrySet()) {
          meterRegistry.gauge("tmd.trading.item.price",
              Tags.of(Tag.of("round.number", round.getKey()), Tag.of("item.name", item)),
              round.getValue());
          meterRegistry.gauge("tmd.trading.item.price.%s".formatted(item),
              Tags.of(Tag.of("round.number", round.getKey())), round.getValue());
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
      resourcePriceHistory = tradingRestClient.getResourcePriceHistory();

      for (var entry : resourcePriceHistory) {
        var resource = entry.name();
        for (var round : entry.history().entrySet()) {
          meterRegistry.gauge("tmd.trading.resource.price",
              Tags.of(Tag.of("round.number", round.getKey()), Tag.of("resource.name", resource)),
              round.getValue());
          meterRegistry.gauge("tmd.trading.resource.price.%s".formatted(resource),
              Tags.of(Tag.of("round.number", round.getKey())), round.getValue());
        }
      }
    } catch (RuntimeException e) {
      log.error("Could not fetch price history", e);
    }
  }
}

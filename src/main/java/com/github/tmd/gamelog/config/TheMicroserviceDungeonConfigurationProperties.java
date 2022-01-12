package com.github.tmd.gamelog.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * We define some configuration Properties here in a type-safe manner.
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "tmd")
@Getter
public class TheMicroserviceDungeonConfigurationProperties {

  private final String gameServiceUrl;
  private final String mapServiceUrl;
  private final String robotServiceUrl;
  private final String tradingServiceUrl;
  private final String gamelogServiceUrl;

  public TheMicroserviceDungeonConfigurationProperties(String gameServiceUrl,
      String mapServiceUrl, String robotServiceUrl, String tradingServiceUrl,
      String gamelogServiceUrl) {
    this.gameServiceUrl = gameServiceUrl;
    this.mapServiceUrl = mapServiceUrl;
    this.robotServiceUrl = robotServiceUrl;
    this.tradingServiceUrl = tradingServiceUrl;
    this.gamelogServiceUrl = gamelogServiceUrl;
  }
}

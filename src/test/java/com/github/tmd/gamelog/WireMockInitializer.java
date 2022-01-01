package com.github.tmd.gamelog;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import java.util.Map;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

public class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort());
    wireMockServer.start();

    applicationContext.getBeanFactory()
            .registerSingleton("wireMockServer", wireMockServer);

    applicationContext.addApplicationListener(event -> {
      if (event instanceof ContextClosedEvent) {
        wireMockServer.stop();;
      }
    });

    var wiremockUrl = "http://localhost:" + wireMockServer.port();

    TestPropertyValues
        .of(Map.of("tmd.game-service-url", wiremockUrl,
            "tmd.gamelog-service-url", wiremockUrl,
            "tmd.map-service-url", wiremockUrl,
            "tmd.robot-service-url", wiremockUrl,
            "tmd.trading-service-url", wiremockUrl))
        .applyTo(applicationContext);
  }
}

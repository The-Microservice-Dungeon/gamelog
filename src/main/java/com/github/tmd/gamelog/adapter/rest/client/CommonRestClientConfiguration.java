package com.github.tmd.gamelog.adapter.rest.client;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonRestClientConfiguration {

  /**
   * It might be possible that we will receive an error during the invocation
   * of a remote REST method. Therefore, we will provide a Retryer.
   */
  @Bean
  public Retryer retryer() {
    return new Retryer.Default();
  }
}

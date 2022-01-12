package com.github.tmd.gamelog.adapter.rest.client;

import com.github.tmd.gamelog.adapter.rest.client.response.PlayerBalanceEntryDto;
import com.github.tmd.gamelog.adapter.rest.client.response.PlayerBalanceRoundEntryDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * REST Client for the Trading Service with the routes we actually need as defined in
 * <a href="https://the-microservice-dungeon.github.io/docs/openapi/trading">Trading OpenAPI</a>
 */
@FeignClient(name = "trading-service", url = "${tmd.trading-service-url}")
public interface TradingRestClient {
  @RequestMapping(method = RequestMethod.GET, path = "balances")
  List<PlayerBalanceEntryDto> getAllPlayersAccountBalances();

  @RequestMapping(method = RequestMethod.GET, path = "balances/{round-number}")
  List<PlayerBalanceRoundEntryDto> getAllPlayerBalancesOfRound(@PathVariable("round-number") Integer roundNumber);
}

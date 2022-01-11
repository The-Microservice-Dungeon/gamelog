package com.github.tmd.gamelog.adapter.rest_client.client;

import com.github.tmd.gamelog.adapter.rest_client.client.response.PlayerBalanceEntryDto;
import com.github.tmd.gamelog.adapter.rest_client.client.response.PlayerBalanceRoundEntryDto;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "trading-service", url = "${tmd.trading-service-url}")
public interface TradingRestClient {
  @RequestMapping(method = RequestMethod.GET, path = "balances")
  List<PlayerBalanceEntryDto> getAllPlayersAccountBalances();

  @RequestMapping(method = RequestMethod.GET, path = "balances/{round-number}")
  List<PlayerBalanceRoundEntryDto> getAllPlayerBalancesOfRound(@PathVariable("round-number") Integer roundNumber);
}

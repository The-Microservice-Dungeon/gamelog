package com.github.tmd.gamelog.adapter.rest_client.client;

import com.github.tmd.gamelog.adapter.rest_client.client.response.PlayerBalanceEntryDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "trading-service", url = "${tmd.trading-service-url}")
public interface TradingRestClient {
  @RequestMapping(method = RequestMethod.GET, path = "balances")
  List<PlayerBalanceEntryDto> getAllPlayersAccountBalances();
}

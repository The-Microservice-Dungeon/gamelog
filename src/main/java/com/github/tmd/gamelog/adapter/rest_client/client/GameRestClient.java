package com.github.tmd.gamelog.adapter.rest_client.client;

import com.github.tmd.gamelog.adapter.rest_client.client.response.CommandContextDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "game-service", url = "${tmd.game-service-url}")
public interface GameRestClient {
  @RequestMapping(method = RequestMethod.GET, value = "/todo/{transaction-id}")
  CommandContextDto getCommandContextForTransactionId(@PathVariable("transaction-id") String transactionId);
}

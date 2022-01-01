package com.github.tmd.gamelog.adapter.rest_client.client;

import com.github.tmd.gamelog.adapter.rest_client.client.response.CommandContextDto;
import com.github.tmd.gamelog.adapter.rest_client.client.response.GetRoundCommandsDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "game-service", url = "${tmd.game-service-url}")
public interface GameRestClient {
  @RequestMapping(method = RequestMethod.GET, value = "/todo/{transaction-id}")
  CommandContextDto getCommandContextForTransactionId(@PathVariable("transaction-id") String transactionId);

  @RequestMapping(method = RequestMethod.GET, path = "commands")
  GetRoundCommandsDto getRoundCommands(@RequestParam("gameId") UUID gameId, @RequestParam("roundNumber") Integer roundNumber);
}

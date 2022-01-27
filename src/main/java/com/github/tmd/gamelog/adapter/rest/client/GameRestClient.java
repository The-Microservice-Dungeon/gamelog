package com.github.tmd.gamelog.adapter.rest.client;

import com.github.tmd.gamelog.adapter.rest.client.response.CommandContextDto;
import com.github.tmd.gamelog.adapter.rest.client.response.GetGamePlayersDto;
import com.github.tmd.gamelog.adapter.rest.client.response.GetRoundCommandsDto;
import java.util.UUID;
import javax.websocket.server.PathParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * REST Client for the Game Service with the routes we actually need as defined in
 * <a href="https://the-microservice-dungeon.github.io/docs/openapi/game">Game OpenAPI</a>
 */
@FeignClient(name = "game-service", url = "${tmd.game-service-url}")
public interface GameRestClient {
  @RequestMapping(method = RequestMethod.GET, value = "/todo/{transaction-id}")
  CommandContextDto getCommandContextForTransactionId(@PathVariable("transaction-id") String transactionId);

  @RequestMapping(method = RequestMethod.GET, path = "commands")
  GetRoundCommandsDto getRoundCommands(@RequestParam("gameId") UUID gameId, @RequestParam("roundNumber") Integer roundNumber);

  @RequestMapping(method = RequestMethod.GET, path = "games/{id}/participatingPlayers")
  GetGamePlayersDto getParticipatingPlayers(@PathVariable("id") UUID gameId);
}

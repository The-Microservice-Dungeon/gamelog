package com.github.tmd.gamelog.adapter.rest_client.client;

import com.github.tmd.gamelog.adapter.rest_client.client.response.RobotDto;
import java.util.Set;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "robot-service", url = "${tmd.robot-service-url}")
public interface RobotRestClient {
  @RequestMapping(method = RequestMethod.GET, value = "/robots/{id}")
  RobotDto getRobotById(@PathVariable("id") String robotId);

  @RequestMapping(method = RequestMethod.GET, value = "robots")
  Set<RobotDto> getRobotsOfPlayer(@RequestParam("player-id") UUID playerId);
}

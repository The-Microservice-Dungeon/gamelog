package com.github.tmd.gamelog.adapter.rest_client;

import com.github.tmd.gamelog.adapter.rest_client.client.RobotServiceRestClient;
import com.github.tmd.gamelog.domain.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerRepository implements com.github.tmd.gamelog.domain.PlayerRepository
{
    private final RobotServiceRestClient robotServiceRestClient;

    public PlayerRepository(RobotServiceRestClient robotServiceRestClient)
    {
        this.robotServiceRestClient = robotServiceRestClient;

    }

    @Override
    public Player findByRobotId(String robotId) {

        RobotDto robotDto = this.robotServiceRestClient.fetchRobotById(robotId);

        Player player = new Player();
        player.setId(robotDto.getPlayer());

        return player;
    }
}

package com.github.tmd.gamelog.adapter.rest;

import com.github.tmd.gamelog.adapter.rest.client.RobotServiceRestClient;
import com.github.tmd.gamelog.domain.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerRepository implements com.github.tmd.gamelog.domain.PlayerRepository
{
    private RobotServiceRestClient robotServiceRestClient;

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

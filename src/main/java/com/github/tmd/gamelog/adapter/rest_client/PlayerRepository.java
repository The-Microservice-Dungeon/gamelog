package com.github.tmd.gamelog.adapter.rest_client;

import com.github.tmd.gamelog.adapter.rest_client.client.RobotRestClient;
import com.github.tmd.gamelog.adapter.rest_client.client.response.RobotDto;
import com.github.tmd.gamelog.domain.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerRepository implements com.github.tmd.gamelog.domain.PlayerRepository
{
    private final RobotRestClient robotRestClient;

    public PlayerRepository(RobotRestClient robotRestClient) {
        this.robotRestClient = robotRestClient;

    }

    @Override
    public Player findByRobotId(String robotId) {

        RobotDto robotDto = this.robotRestClient.getRobotById(robotId);

        Player player = new Player();
        player.setId(robotDto.player());

        return player;
    }
}

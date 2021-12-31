package com.github.tmd.gamelog.adapter.rest_client;

import com.github.tmd.gamelog.adapter.rest_client.client.RobotRestClient;
import com.github.tmd.gamelog.adapter.rest_client.client.response.RobotDto;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.PlayerRepository;
import org.springframework.stereotype.Component;

@Component(value = "legacy-player-repository")
public class PlayerRepositoryImpl implements PlayerRepository
{
    private final RobotRestClient robotRestClient;

    public PlayerRepositoryImpl(RobotRestClient robotRestClient) {
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

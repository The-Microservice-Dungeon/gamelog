package com.github.tmd.gamelog.adapter.rest_client.client;

import com.github.tmd.gamelog.adapter.rest_client.RobotDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;

@Component
public class RobotServiceRestClient extends AbstractRestClient {

    @Autowired
    public RobotServiceRestClient(RestTemplateBuilder restTemplateBuilder,
        @Value("${tmd.robot-service-url}") String gameServiceUrl)
    {
        super(restTemplateBuilder);
        this.setBaseUrl(gameServiceUrl);
    }

    public RobotDto fetchRobotById(String id)
    {
        RobotDto robotDto = new RobotDto();
        robotDto.setPlayer("player1");
        return robotDto;

        //TODO get Robot
        /*RestQuery restQuery = this.createRestQuery();

        restQuery.setEndpoint("/robot/" + id);
        restQuery.setResultingClass(RobotDto.class);

        return (RobotDto) restQuery.fetch();*/
    }
}

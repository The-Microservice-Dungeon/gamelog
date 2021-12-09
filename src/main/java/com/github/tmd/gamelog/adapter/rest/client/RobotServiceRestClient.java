package com.github.tmd.gamelog.adapter.rest.client;

import com.github.tmd.gamelog.adapter.rest.RestQuery;
import com.github.tmd.gamelog.adapter.rest.RobotDto;
import org.springframework.boot.web.client.RestTemplateBuilder;

public class RobotServiceRestClient extends AbstractRestClient {

    public RobotServiceRestClient(RestTemplateBuilder restTemplateBuilder)
    {
        super(restTemplateBuilder);
    }

    public RobotDto fetchRobotById(String id)
    {
        RestQuery restQuery = this.createRestQuery(RobotDto.class);

        restQuery.setEndpoint("/robot/sdsdsd");

        return restQuery.fetch();
    }
}

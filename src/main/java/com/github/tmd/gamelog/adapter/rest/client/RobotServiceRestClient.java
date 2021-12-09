package com.github.tmd.gamelog.adapter.rest.client;

import com.github.tmd.gamelog.adapter.rest.RestQuery;
import com.github.tmd.gamelog.adapter.rest.RobotDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;

public class RobotServiceRestClient extends AbstractRestClient {

    public RobotServiceRestClient(RestTemplateBuilder restTemplateBuilder, Environment environment)
    {
        super(restTemplateBuilder);

        this.setBaseUrl(environment.getProperty("ROBOT_SERVICE"));
    }

    public RobotDto fetchRobotById(String id)
    {
        RestQuery restQuery = this.createRestQuery();

        restQuery.setEndpoint("/robot/" + id);
        restQuery.setResultingClass(RobotDto.class);

        return (RobotDto) restQuery.fetch();
    }
}

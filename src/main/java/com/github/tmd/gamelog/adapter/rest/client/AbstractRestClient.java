package com.github.tmd.gamelog.adapter.rest.client;

import com.github.tmd.gamelog.adapter.rest.RestQuery;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


public abstract class AbstractRestClient {
    @Getter
    @Setter
    private String baseUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public AbstractRestClient(RestTemplateBuilder restTemplateBuilder)
    {
        this.restTemplate = restTemplateBuilder.build();
    }

    public RestQuery createRestQuery()
    {
        RestQuery restQuery = new RestQuery();

        restQuery.setBaseUrl(this.getBaseUrl());
        restQuery.setRestTemplate(this.restTemplate);

        return restQuery;
    }
}

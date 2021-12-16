package com.github.tmd.gamelog.adapter.rest_client.client;

import com.github.tmd.gamelog.adapter.rest_client.RestQuery;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;


public abstract class AbstractRestClient {
    @Getter
    @Setter
    private String baseUrl;
    private final RestTemplate restTemplate;

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

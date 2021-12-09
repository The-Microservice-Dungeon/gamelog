package com.github.tmd.gamelog.adapter.rest.client;

import com.github.tmd.gamelog.adapter.rest.RestQuery;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractRestClient {
    private RestTemplateBuilder restTemplateBuilder;

    public AbstractRestClient(RestTemplateBuilder restTemplateBuilder)
    {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public RestTemplate createRestTemplate()
    {
        return this.restTemplateBuilder.build();
    }

    public RestQuery createRestQuery()
    {
        RestQuery restQuery = new RestQuery();

        restQuery.setRestTemplate(this.createRestTemplate());

        return restQuery;
    }
}

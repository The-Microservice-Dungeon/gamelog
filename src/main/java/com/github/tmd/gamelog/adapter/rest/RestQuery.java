package com.github.tmd.gamelog.adapter.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.client.RestTemplate;

@Getter
@Setter
@NoArgsConstructor
public class RestQuery {
    private String baseUrl;
    private String endpoint;
    private Class resultingClass;
    private RestTemplate restTemplate;

    public Object fetch()
    {
        return restTemplate.getForObject(this.baseUrl + this.endpoint, this.resultingClass);
    }
}

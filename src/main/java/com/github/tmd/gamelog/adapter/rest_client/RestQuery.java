package com.github.tmd.gamelog.adapter.rest_client;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.client.RestTemplate;

@Data
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

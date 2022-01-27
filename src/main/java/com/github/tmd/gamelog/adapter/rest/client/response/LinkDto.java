package com.github.tmd.gamelog.adapter.rest.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.net.URL;

public record LinkDto(
    @JsonProperty("href") URI href
    ) {

}

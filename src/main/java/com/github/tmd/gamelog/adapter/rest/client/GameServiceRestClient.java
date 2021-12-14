package com.github.tmd.gamelog.adapter.rest.client;

import com.github.tmd.gamelog.domain.CommandContext;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

@Component
public class GameServiceRestClient extends AbstractRestClient {

    public GameServiceRestClient(RestTemplateBuilder restTemplateBuilder, Environment environment) {
        super(restTemplateBuilder);

        this.setBaseUrl(environment.getProperty("GAME_SERVICE"));

    }

    public Object getCommandContextForTransactionId(String transactionId) {
        CommandContext result = new CommandContext();
        result.setGameId("1");
        result.setPlayerId("2");
        result.setRound(3);
        return result;
    }
}

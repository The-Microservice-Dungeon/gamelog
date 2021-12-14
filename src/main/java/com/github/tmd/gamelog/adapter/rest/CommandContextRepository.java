package com.github.tmd.gamelog.adapter.rest;

import com.github.tmd.gamelog.adapter.rest.client.GameServiceRestClient;
import com.github.tmd.gamelog.domain.CommandContext;
import org.springframework.stereotype.Component;

@Component
public class CommandContextRepository implements com.github.tmd.gamelog.domain.CommandContextRepository {

    private final GameServiceRestClient gameServiceRestClient;

    public CommandContextRepository(GameServiceRestClient gameServiceRestClient) {
        this.gameServiceRestClient = gameServiceRestClient;
    }

    public CommandContext findByTransactionId(String transactionId) {
        return this.gameServiceRestClient.fetchCommandContextForTransactionId(transactionId);
    }
}

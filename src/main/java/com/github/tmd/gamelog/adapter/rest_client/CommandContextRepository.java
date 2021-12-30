package com.github.tmd.gamelog.adapter.rest_client;

import com.github.tmd.gamelog.adapter.rest_client.client.GameRestClient;
import com.github.tmd.gamelog.domain.CommandContext;
import org.springframework.stereotype.Component;

@Component
public class CommandContextRepository implements com.github.tmd.gamelog.domain.CommandContextRepository {

    private final GameRestClient gameRestClient;
    private final CommandContextMapper commandContextMapper;

    public CommandContextRepository(GameRestClient gameRestClient) {
        this.gameRestClient = gameRestClient;
        this.commandContextMapper = new CommandContextMapper();
    }

    public CommandContext findByTransactionId(String transactionId) {
        var response = this.gameRestClient.getCommandContextForTransactionId(transactionId);
        return commandContextMapper.toDomain(response);
    }
}

package com.github.tmd.gamelog.adapter.rest_client;

import com.github.tmd.gamelog.adapter.rest_client.client.GameRestClient;
import com.github.tmd.gamelog.domain.CommandContext;
import com.github.tmd.gamelog.domain.CommandContextRepository;
import org.springframework.stereotype.Component;

@Component
public class CommandContextRepositoryImpl implements CommandContextRepository {

    private final GameRestClient gameRestClient;
    private final CommandContextMapper commandContextMapper;

    public CommandContextRepositoryImpl(GameRestClient gameRestClient) {
        this.gameRestClient = gameRestClient;
        this.commandContextMapper = new CommandContextMapper();
    }

    public CommandContext findByTransactionId(String transactionId) {
        var response = this.gameRestClient.getCommandContextForTransactionId(transactionId);
        return commandContextMapper.toDomain(response);
    }
}

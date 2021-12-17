package com.github.tmd.gamelog.adapter.rest_client.client;

import com.github.tmd.gamelog.adapter.rest_client.RestQuery;
import com.github.tmd.gamelog.adapter.rest_client.client.responseDto.CommandContextDto;
import com.github.tmd.gamelog.adapter.rest_client.client.responseDto.CommandContextDtoMapper;
import com.github.tmd.gamelog.domain.CommandContext;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.Round;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

@Component
public class GameServiceRestClient extends AbstractRestClient {

    public GameServiceRestClient(
            RestTemplateBuilder restTemplateBuilder,
            Environment environment
    ) {
        super(restTemplateBuilder);

        this.setBaseUrl(environment.getProperty("GAME_SERVICE"));
    }

    public CommandContext fetchCommandContextForTransactionId(String transactionId) {
        CommandContext result = new CommandContext();

        result.setPlayer(new Player("2030-2-20302--3000302020202020"));
        result.setRound(new Round("sds", 0, "02-203-203"));

        return result;

        /*RestQuery restQuery = this.createRestQuery();

        restQuery.setEndpoint("/todo/" + transactionId);
        restQuery.setResultingClass(CommandContextDto.class);

        CommandContextDto commandContextDto = (CommandContextDto) restQuery.fetch();
        return CommandContextDtoMapper.mapDtoToEntity(commandContextDto);*/
    }
}

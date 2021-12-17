package com.github.tmd.gamelog.adapter.rest_client.client.responseDto;

import com.github.tmd.gamelog.domain.CommandContext;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.Round;

public class CommandContextDtoMapper {
    public static CommandContext mapDtoToEntity(CommandContextDto commandContextDto) {
        Player player = new Player(commandContextDto.getPlayerId());
        Round round = new Round(commandContextDto.getGameId(), commandContextDto.getRoundNumber(), commandContextDto.getRoundId());
        return new CommandContext(round, player);
    }
}

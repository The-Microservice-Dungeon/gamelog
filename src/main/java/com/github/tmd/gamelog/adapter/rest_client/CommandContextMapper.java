package com.github.tmd.gamelog.adapter.rest_client;

import com.github.tmd.gamelog.adapter.rest_client.client.response.CommandContextDto;
import com.github.tmd.gamelog.domain.CommandContext;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.Round;

public class CommandContextMapper {
  public CommandContext toDomain(CommandContextDto dto) {
    var player = new Player(dto.playerId());
    var round = new Round(dto.gameId(), dto.roundNumber(), dto.roundId());
    return new CommandContext(round, player);
  }
}

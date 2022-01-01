package com.github.tmd.gamelog.application.service;

import com.github.tmd.gamelog.adapter.jpa.history.game.CommandHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.game.CommandHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.rest_client.client.GameRestClient;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CommandHistoryService {
  private final CommandHistoryJpaRepository commandHistoryJpaRepository;
  private final GameRestClient gameRestClient;

  public CommandHistoryService(
      CommandHistoryJpaRepository commandHistoryJpaRepository,
      GameRestClient gameRestClient) {
    this.commandHistoryJpaRepository = commandHistoryJpaRepository;
    this.gameRestClient = gameRestClient;
  }

  @Transactional
  public void getAndSaveAllExecutedCommandsInRound(UUID gameId, Integer roundNumber) {
    var res = gameRestClient.getRoundCommands(gameId, roundNumber);
    var executedCommands = res
        .commands()
        .stream().map(ec -> new CommandHistoryJpa(ec.transactionId(), ec.gameId(), res.roundId(), ec.playerId()))
        .collect(Collectors.toSet());
    this.commandHistoryJpaRepository.saveAll(executedCommands);
  }
}

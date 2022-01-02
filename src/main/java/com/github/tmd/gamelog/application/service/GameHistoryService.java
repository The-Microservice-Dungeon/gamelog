package com.github.tmd.gamelog.application.service;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatus;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.LobbyAction;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatus;
import com.github.tmd.gamelog.adapter.jpa.history.game.CommandHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.game.CommandHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.game.GamePlayerStatusHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.game.GamePlayerStatusHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.game.GamePlayerStatusJpa;
import com.github.tmd.gamelog.adapter.jpa.history.game.GameRoundStatusHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.game.GameRoundStatusHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.game.GameRoundStatusJpa;
import com.github.tmd.gamelog.adapter.jpa.history.game.GameStatusHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.game.GameStatusHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.game.GameStatusJpa;
import com.github.tmd.gamelog.adapter.rest_client.client.GameRestClient;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class GameHistoryService {

  private final CommandHistoryJpaRepository commandHistoryJpaRepository;
  private final GamePlayerStatusHistoryJpaRepository gamePlayerStatusHistoryJpaRepository;
  private final GameRoundStatusHistoryJpaRepository gameRoundStatusHistoryJpaRepository;
  private final GameStatusHistoryJpaRepository gameStatusHistoryJpaRepository;
  private final GameRestClient gameRestClient;

  public GameHistoryService(
      CommandHistoryJpaRepository commandHistoryJpaRepository,
      GamePlayerStatusHistoryJpaRepository gamePlayerStatusHistoryJpaRepository,
      GameRoundStatusHistoryJpaRepository gameRoundStatusHistoryJpaRepository,
      GameStatusHistoryJpaRepository gameStatusHistoryJpaRepository,
      GameRestClient gameRestClient) {
    this.commandHistoryJpaRepository = commandHistoryJpaRepository;
    this.gamePlayerStatusHistoryJpaRepository = gamePlayerStatusHistoryJpaRepository;
    this.gameRoundStatusHistoryJpaRepository = gameRoundStatusHistoryJpaRepository;
    this.gameStatusHistoryJpaRepository = gameStatusHistoryJpaRepository;
    this.gameRestClient = gameRestClient;
  }

  public Set<UUID> getAllParticipatingPlayersInGame(UUID gameId) {
    return gamePlayerStatusHistoryJpaRepository.findAllParticipatingPlayersInGame(gameId);
  }

  @Transactional
  public void insertExecutedCommandsHistory(UUID gameId, Integer roundNumber) {
    var res = gameRestClient.getRoundCommands(gameId, roundNumber);
    var executedCommands = res
        .commands()
        .stream().map(ec -> new CommandHistoryJpa(ec.transactionId(), ec.gameId(), res.roundId(),
            ec.playerId()))
        .collect(Collectors.toSet());
    this.commandHistoryJpaRepository.saveAll(executedCommands);
  }

  @Transactional
  public void insertGamePlayerStatusHistory(UUID gameId, UUID playerId, String userName, LobbyAction status) {
    this.gamePlayerStatusHistoryJpaRepository.save(
        new GamePlayerStatusHistoryJpa(gameId, playerId, userName,
            GamePlayerStatusJpa.fromLobbyAction(status)));
  }

  @Transactional
  public void insertGameRoundStatusHistory(UUID gameId, UUID roundId, Integer roundNumber,
      RoundStatus roundStatus) {
    this.gameRoundStatusHistoryJpaRepository.save(
        new GameRoundStatusHistoryJpa(gameId, roundId, roundNumber,
            GameRoundStatusJpa.fromRoundStatus(roundStatus)));
  }

  @Transactional
  public void insertGameStatusHistory(UUID gameId, GameStatus gameStatus) {
    this.gameStatusHistoryJpaRepository.save(
        new GameStatusHistoryJpa(gameId, GameStatusJpa.fromGameStatus(gameStatus)));
  }
}

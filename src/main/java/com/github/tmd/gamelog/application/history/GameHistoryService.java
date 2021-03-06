package com.github.tmd.gamelog.application.history;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatus;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.LobbyAction;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatus;
import com.github.tmd.gamelog.adapter.jpa.history.game.CommandHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.game.CommandHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.game.GamePlayerStatusHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.game.GamePlayerStatusHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.game.GameRoundStatusHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.game.GameRoundStatusHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.game.GameRoundStatusJpa;
import com.github.tmd.gamelog.adapter.jpa.history.game.GameStatusHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.game.GameStatusHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.game.GameStatusJpa;
import com.github.tmd.gamelog.adapter.rest.client.GameRestClient;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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

  /**
   *
   * @throws RuntimeException if a error occurs in the synchronous call
   */
  @Transactional
  public void insertExecutedCommandsHistory(UUID gameId, Integer roundNumber) {
    try {
      var res = gameRestClient.getRoundCommands(gameId, roundNumber);
      var executedCommands = res
          .commands()
          .stream().map(ec -> new CommandHistoryJpa(ec.transactionId(), ec.gameId(), res.roundId(),
              ec.playerId()))
          .collect(Collectors.toSet());
      this.commandHistoryJpaRepository.saveAll(executedCommands);
    } catch (RuntimeException e) {
      log.error("Could not load round commands", e);
      throw e;
    }
  }

  @Transactional
  public void insertGamePlayerStatusHistory(UUID gameId, UUID playerId, String userName, Temporal timestamp) {
    if (userName == null || userName.isBlank()) {
      userName = playerId.toString();
    }
    this.gamePlayerStatusHistoryJpaRepository.save(
        new GamePlayerStatusHistoryJpa(gameId, playerId, userName, Instant.from(timestamp)));
  }

  @Transactional
  public void insertGameRoundStatusHistory(UUID gameId, UUID roundId, Integer roundNumber,
      RoundStatus roundStatus, Temporal timestamp) {
    this.gameRoundStatusHistoryJpaRepository.save(
        new GameRoundStatusHistoryJpa(gameId, roundId, roundNumber,
            GameRoundStatusJpa.fromRoundStatus(roundStatus), Instant.from(timestamp)));
  }

  @Transactional
  public void insertGameStatusHistory(UUID gameId, GameStatus gameStatus, Temporal timestamp) {
    this.gameStatusHistoryJpaRepository.save(
        new GameStatusHistoryJpa(gameId, GameStatusJpa.fromGameStatus(gameStatus), Instant.from(timestamp)));
  }

  @Transactional
  public boolean playerHistoryExists(UUID gameId, UUID playerId) {
    return this.gamePlayerStatusHistoryJpaRepository.existsByGameIdAndUserId(gameId, playerId);
  }
}

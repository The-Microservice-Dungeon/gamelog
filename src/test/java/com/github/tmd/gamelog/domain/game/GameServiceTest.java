package com.github.tmd.gamelog.domain.game;

import static org.assertj.core.api.Assertions.*;

import com.github.tmd.gamelog.adapter.jpa.game.GameJpa;
import com.github.tmd.gamelog.adapter.jpa.game.GameJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.game.RoundJpa;
import com.github.tmd.gamelog.adapter.jpa.player.PlayerJpa;
import com.github.tmd.gamelog.adapter.jpa.player.PlayerJpaRepository;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class GameServiceTest {

  @Autowired
  GameJpaRepository gameJpaRepository;

  @Autowired
  PlayerJpaRepository playerJpaRepository;

  @Autowired
  GameService gameService;

  @Test
  void shouldSaveGameInDb_WhenGameCreate() {
    // Given
    var gameId = UUID.randomUUID();

    // When
    gameService.createNewGame(gameId);

    // Then
    var found = gameJpaRepository.findById(gameId);
    assertThat(found).isNotEmpty();
    var game = found.get();
    assertThat(game.getGameId()).isEqualTo(gameId);
    assertThat(game.getStatus()).isEqualTo(GameStatus.CREATED);
  }

  @Test
  void shouldUpdateGameStatusInDb_WhenGameStart() {
    // Given
    var gameId = UUID.randomUUID();
    var givenGame = new GameJpa();
    givenGame.setGameId(gameId);
    givenGame.setStatus(GameStatus.CREATED);
    gameJpaRepository.save(givenGame);

    // When
    gameService.startGame(gameId);

    // Then
    var found = gameJpaRepository.findById(gameId);
    assertThat(found).isNotEmpty();
    var game = found.get();
    assertThat(game.getGameId()).isEqualTo(gameId);
    assertThat(game.getStatus()).isEqualTo(GameStatus.STARTED);
  }

  @Test
  void shouldUpdateGameStatusInDb_WhenGameEnd() {
    // Given
    var gameId = UUID.randomUUID();
    var givenGame = new GameJpa();
    givenGame.setGameId(gameId);
    givenGame.setStatus(GameStatus.STARTED);
    gameJpaRepository.save(givenGame);

    // When
    gameService.endGame(gameId);

    // Then
    var found = gameJpaRepository.findById(gameId);
    assertThat(found).isNotEmpty();
    var game = found.get();
    assertThat(game.getGameId()).isEqualTo(gameId);
    assertThat(game.getStatus()).isEqualTo(GameStatus.ENDED);
  }

  @Test
  void shouldAddRoundInDb_WhenRoundStart() {
    // Given
    var gameId = UUID.randomUUID();
    var givenGame = new GameJpa();
    givenGame.setGameId(gameId);
    givenGame.setStatus(GameStatus.STARTED);
    gameJpaRepository.save(givenGame);
    var roundId = UUID.randomUUID();
    var roundNumber = 0;

    // When
    gameService.addRound(gameId, roundId, roundNumber);

    // Then
    var found = gameJpaRepository.findById(gameId);
    assertThat(found).isNotEmpty();
    var game = found.get();
    assertThat(game.getRounds()).hasSize(1);
    assertThat(game.getRounds()).extracting("roundId", "roundNumber")
        .containsExactlyInAnyOrder(tuple(roundId, roundNumber));
  }

  @Test
  void shouldAddRoundScoreInDb_WhenAdScore() {
    // Given
    var gameId = UUID.randomUUID();
    var roundId = UUID.randomUUID();
    var playerId = UUID.randomUUID();

    var givenPlayer = new PlayerJpa();
    givenPlayer.setPlayerId(playerId);
    givenPlayer.setUserName("mmustermann");
    playerJpaRepository.save(givenPlayer);

    var givenRound = new RoundJpa();
    givenRound.setRoundId(roundId);

    var givenGame = new GameJpa();
    givenGame.setGameId(gameId);
    givenGame.setStatus(GameStatus.STARTED);
    givenGame.setRounds(Set.of(givenRound));
    gameJpaRepository.save(givenGame);

    // When
    gameService.addScore(gameId, roundId, playerId, 12);

    // Then
    var game = gameJpaRepository.findById(gameId).orElseThrow();
    var round = game.getRounds().stream().filter(r -> r.getRoundId().equals(roundId)).findFirst().orElseThrow();
    var score = round.getScores().stream().filter(s -> s.getPlayer().getPlayerId().equals(playerId)).findFirst().orElseThrow();

    assertThat(score.getTestScore()).isEqualTo(12);
  }
}
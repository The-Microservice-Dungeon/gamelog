package com.github.tmd.gamelog.domain.game;

import static org.assertj.core.api.Assertions.*;

import com.github.tmd.gamelog.adapter.jpa.game.GameJpa;
import com.github.tmd.gamelog.adapter.jpa.game.GameJpaRepository;
import java.time.ZonedDateTime;
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
}
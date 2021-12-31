package com.github.tmd.gamelog.domain.game;

import static org.junit.jupiter.api.Assertions.*;
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
  GameEventHandler gameEventHandler;

  @Test
  void createNewGame() {
    // Given
    var gameId = UUID.randomUUID().toString();

    // When
    gameEventHandler.onGameCreate(gameId, ZonedDateTime.now());

    // Then
    var found = gameJpaRepository.findByGameId(gameId);
    assertThat(found).isNotEmpty();
    var game = found.get();
    assertThat(game.getGameId()).isEqualTo(gameId);
    assertThat(game.getStatus()).isEqualTo(GameStatus.CREATED);
  }

  @Test
  void startGame() {
    // Given
    var gameId = UUID.randomUUID().toString();
    var givenGame = new GameJpa();
    givenGame.setGameId(gameId);
    givenGame.setStatus(GameStatus.CREATED);
    gameJpaRepository.save(givenGame);

    // When
    gameEventHandler.onGameStart(gameId, ZonedDateTime.now());

    // Then
    var found = gameJpaRepository.findByGameId(gameId);
    assertThat(found).isNotEmpty();
    var game = found.get();
    assertThat(game.getGameId()).isEqualTo(gameId);
    assertThat(game.getStatus()).isEqualTo(GameStatus.STARTED);
  }

  @Test
  void endGame() {
    // Given
    var gameId = UUID.randomUUID().toString();
    var givenGame = new GameJpa();
    givenGame.setGameId(gameId);
    givenGame.setStatus(GameStatus.STARTED);
    gameJpaRepository.save(givenGame);

    // When
    gameEventHandler.onGameEnd(gameId, ZonedDateTime.now());

    // Then
    var found = gameJpaRepository.findByGameId(gameId);
    assertThat(found).isNotEmpty();
    var game = found.get();
    assertThat(game.getGameId()).isEqualTo(gameId);
    assertThat(game.getStatus()).isEqualTo(GameStatus.ENDED);
  }
}
package com.github.tmd.gamelog.adapter.jpa.history.game;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class GamePlayerStatusHistoryJpaRepositoryTest {
  @Autowired
  GamePlayerStatusHistoryJpaRepository gamePlayerStatusHistoryJpaRepository;

  @Test
  void shouldReturnAllParticipatingPlayers() {
    // Given
    var game1Id = UUID.randomUUID();
    var game2Id = UUID.randomUUID();

    var player1Id = UUID.randomUUID();
    var player1Name = "player1";
    var player2Id = UUID.randomUUID();
    var player2Name = "player2";
    var player3Id = UUID.randomUUID();
    var player3Name = "player3";

    // Player 1 joins game and stays
    var p0  = new GamePlayerStatusHistoryJpa(game1Id, player1Id, player1Name, GamePlayerStatusJpa.LEFT, Instant.now());

    // Player 2 joins, then leaves - also he registers for game 2
    var p1  = new GamePlayerStatusHistoryJpa(game1Id, player2Id, player2Name, GamePlayerStatusJpa.JOINED, Instant.now());
    var p2  = new GamePlayerStatusHistoryJpa(game1Id, player2Id, player2Name, GamePlayerStatusJpa.LEFT, Instant.now());
    var p3  = new GamePlayerStatusHistoryJpa(game2Id, player2Id, player2Name, GamePlayerStatusJpa.JOINED, Instant.now());

    // Player 3 joins, then leaves and joins back - also he registers for game 2
    var p4  = new GamePlayerStatusHistoryJpa(game1Id, player3Id, player3Name, GamePlayerStatusJpa.JOINED, Instant.now());
    var p5  = new GamePlayerStatusHistoryJpa(game1Id, player3Id, player3Name, GamePlayerStatusJpa.LEFT, Instant.now());
    var p6  = new GamePlayerStatusHistoryJpa(game1Id, player3Id, player3Name, GamePlayerStatusJpa.JOINED, Instant.now());
    var p7  = new GamePlayerStatusHistoryJpa(game2Id, player3Id, player3Name, GamePlayerStatusJpa.JOINED, Instant.now());

    // Saving individual instead of saveAll() to maintain the creation timestamp
    gamePlayerStatusHistoryJpaRepository.save(p0);
    gamePlayerStatusHistoryJpaRepository.save(p1);
    gamePlayerStatusHistoryJpaRepository.save(p2);
    gamePlayerStatusHistoryJpaRepository.save(p3);
    gamePlayerStatusHistoryJpaRepository.save(p4);
    gamePlayerStatusHistoryJpaRepository.save(p5);
    gamePlayerStatusHistoryJpaRepository.save(p6);
    gamePlayerStatusHistoryJpaRepository.save(p7);

    // When
    var all =StreamSupport.stream(gamePlayerStatusHistoryJpaRepository.findAll().spliterator(), false).collect(
        Collectors.toSet());
    var result = gamePlayerStatusHistoryJpaRepository.findAllParticipatingPlayersInGame(game1Id);

    // Then
    assertThat(result).hasSize(2);
    assertThat(result).containsExactlyInAnyOrder(player2Id, player3Id); // Only players 2 and 3 participate
  }
}
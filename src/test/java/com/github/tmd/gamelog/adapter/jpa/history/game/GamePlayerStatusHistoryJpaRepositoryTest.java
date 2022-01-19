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

    var p0  = new GamePlayerStatusHistoryJpa(game1Id, player1Id, player1Name, Instant.now());
    var p1  = new GamePlayerStatusHistoryJpa(game1Id, player2Id, player2Name, Instant.now());
    var p2  = new GamePlayerStatusHistoryJpa(game1Id, player3Id, player3Name, Instant.now());
    var p3  = new GamePlayerStatusHistoryJpa(game2Id, player3Id, player3Name, Instant.now());

    // Saving individual instead of saveAll() to maintain the creation timestamp
    gamePlayerStatusHistoryJpaRepository.save(p0);
    gamePlayerStatusHistoryJpaRepository.save(p1);
    gamePlayerStatusHistoryJpaRepository.save(p2);
    gamePlayerStatusHistoryJpaRepository.save(p3);
    // When
    var all =StreamSupport.stream(gamePlayerStatusHistoryJpaRepository.findAll().spliterator(), false).collect(
        Collectors.toSet());
    var result = gamePlayerStatusHistoryJpaRepository.findAllParticipatingPlayersInGame(game1Id);

    // Then
    assertThat(result).hasSize(3);
    assertThat(result).containsExactlyInAnyOrder(player1Id, player2Id, player3Id); // Only players 2 and 3 participate
  }
}
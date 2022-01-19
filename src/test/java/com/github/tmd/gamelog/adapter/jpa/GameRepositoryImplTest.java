package com.github.tmd.gamelog.adapter.jpa;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import com.github.tmd.gamelog.domain.Game.GameId;
import java.util.UUID;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

@SpringBootTest
@Transactional
@Sql(scripts = "classpath:db/mock_history.sql")
class GameRepositoryImplTest {

  @Autowired
  GameRepositoryImpl gameRepository;

  @Test
  void findGameById() {
    // Given
    var gameId = UUID.fromString("22838d5f-3e11-46fd-9074-d76d0e2ad27c");
    var id = new GameId(gameId);

    // When
    var foundGame = this.gameRepository.findGameById(id);

    // Then
    assertThat(foundGame).isNotEmpty()
        .get()
        .extracting("id.id")
        .isEqualTo(gameId);
  }

  @Test
  void findActiveGame() {
    // When
    var foundGame = this.gameRepository.findActiveGame();

    // Then
    var expectedGameId = UUID.fromString("22838d5f-3e11-46fd-9074-d76d0e2ad27c");
    assertThat(foundGame).isNotEmpty()
        .get()
        .extracting("id.id")
        .isEqualTo(expectedGameId);
  }

  @Test
  void findAllGames() {
    // When
    var found = this.gameRepository.findAllGames();

    // Then
    assertThat(found)
        .hasSize(1);
  }
}
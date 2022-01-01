package com.github.tmd.gamelog.domain.player;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;


import com.github.tmd.gamelog.adapter.jpa.player.PlayerJpa;
import com.github.tmd.gamelog.adapter.jpa.player.PlayerJpaRepository;
import com.github.tmd.gamelog.domain.player.PlayerService;
import java.util.UUID;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
public class PlayerServiceTest {

  @Autowired
  PlayerJpaRepository playerJpaRepository;

  @Autowired
  PlayerService playerService;

  @Test
  void shouldReturnPlayer_WhenPlayerRegister() {
    // Given
    var playerId = UUID.randomUUID();
    var userName = "mmustermann";

    // When
    var player = playerService.registerPlayer(playerId, userName);

    // Then
    assertThat(player).isNotNull();
    assertThat(player.getId().playerId()).isEqualTo(playerId);
    assertThat(player.getUserName()).isEqualTo(userName);
  }

  @Test
  void shouldCreatePlayerInDb_WhenPlayerRegisterWithNewUser() {
    // Given
    var playerId = UUID.randomUUID();
    var userName = "mmustermann";

    // When
    playerService.registerPlayer(playerId, userName);

    // Then
    var found = playerJpaRepository.findById(playerId);
    assertThat(found).isNotEmpty();
    var playerJpa = found.get();
    assertThat(playerJpa.getPlayerId()).isEqualTo(playerId);
    assertThat(playerJpa.getUserName()).isEqualTo(userName);
  }

  @Test
  void shouldRetrievePlayerInDb_WhenPlayerRegisterWithExistingUser() {
    // Given
    var playerId = UUID.randomUUID();
    var userName = "mmustermann";
    var existingPlayer = new PlayerJpa(playerId, userName);
    playerJpaRepository.save(existingPlayer);

    // When
    playerService.registerPlayer(playerId, userName);

    // Then
    assertThat(playerJpaRepository.count()).isEqualTo(1);
  }
}
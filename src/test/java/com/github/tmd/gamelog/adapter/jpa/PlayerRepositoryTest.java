package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.adapter.jpa.mapper.PlayerDtoMapper;
import com.github.tmd.gamelog.adapter.jpa.mapper.TrophyDtoMapper;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.PlayerTrophy;
import com.github.tmd.gamelog.domain.trophies.Trophy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for class PlayerRepository.
 */
@SpringBootTest
@Transactional
public class PlayerRepositoryTest {

    @Autowired
    private PlayerJpaRepository playerJpaRepository;
    private PlayerDtoMapper playerDtoMapper;
    private PlayerRepository playerRepository;

    @Autowired
    private TrophyJpaRepository trophyJpaRepository;
    private TrophyRepository trophyRepository;

    @BeforeEach
    void beforeEach() {
        playerDtoMapper = new PlayerDtoMapper();
        playerRepository = new PlayerRepository(playerJpaRepository, playerDtoMapper);
        trophyRepository = new TrophyRepository(trophyJpaRepository, new TrophyDtoMapper());
        trophyRepository.initRepository();
    }

    @Test
    void testUpsertAndFindAll() {
        // Make sure the repo is empty.
        ArrayList<Player> playersFromRepo = playerRepository.findAll();
        assertThat(playersFromRepo).isEmpty();

        // Try to insert a player and check if it can be read from the repo.
        Player player = generatePlayer();
        playerRepository.upsert(player);
        playersFromRepo = playerRepository.findAll();
        assertThat(playersFromRepo).contains(player);
    }

    private Player generatePlayer() {
        Set<PlayerTrophy> playerTrophies = new HashSet<>();
        Trophy trophy = trophyRepository.findAll().get(0);
        PlayerTrophy playerTrophy = new PlayerTrophy(trophy, UUID.randomUUID(), new Date());
        playerTrophies.add(playerTrophy);
        Player player = new Player(UUID.randomUUID(), playerTrophies);
        return player;
    }

}

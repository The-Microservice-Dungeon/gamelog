package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.adapter.jpa.mapper.TrophyDtoMapper;
import com.github.tmd.gamelog.domain.trophies.Trophy;
import com.github.tmd.gamelog.domain.trophies.scoreboard.FightingFirstPlaceTrophy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for class TrophyRepository.
 */
@SpringBootTest
@Transactional
public class TrophyRepositoryTest {

    private final String name = "First Blood";
    private final String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Fighting%20Bronze%20-%20First%20Blood.png";
    @Autowired
    private TrophyJpaRepository trophyJpaRepository;
    private TrophyDtoMapper trophyDtoMapper;
    private TrophyRepository trophyRepository;

    @BeforeEach
    void beforeEach() {
        trophyDtoMapper = new TrophyDtoMapper();
        trophyRepository = new TrophyRepository(trophyJpaRepository, trophyDtoMapper);
    }

    @Test
    void testUpsertAndFindAll() {
        Trophy testTrophy = new Trophy();
        testTrophy.setName(name);
        testTrophy.setBadgeUrl(badgeUrl);
        assertThat(trophyRepository.findAll()).isEmpty();
        trophyRepository.upsert(testTrophy);
        ArrayList<Trophy> trophies = trophyRepository.findAll();
        assertThat(trophies.get(0)).usingRecursiveComparison().ignoringFields("id").isEqualTo(testTrophy);
    }

    @Test
    void testInitRepository() {
        // Prepare repository with at least one trophy.
        trophyRepository.upsert(new FightingFirstPlaceTrophy());
        assertThat(trophyRepository.findAll()).isNotEmpty();

        // Run the init function.
        trophyRepository.initRepository();

        // Check if the list of trophies on the repo matches the list of default trophies (ignoring IDs and order).
        ArrayList<Trophy> requiredTrophies = Trophy.getDefaultTrophies();
        ArrayList<Trophy> trophiesOnTheRepository = trophyRepository.findAll();
        assertThat(trophiesOnTheRepository).usingRecursiveComparison().ignoringFields("id").ignoringCollectionOrder().isEqualTo(requiredTrophies);
    }

}

package com.github.tmd.gamelog.domain;

import com.github.tmd.gamelog.adapter.jpa.TrophyJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.TrophyRepository;
import com.github.tmd.gamelog.adapter.jpa.mapper.TrophyDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for class TrophyRepository.
 */
@SpringBootTest
public class TrophyRepositoryTest {

    @Autowired
    private TrophyJpaRepository trophyJpaRepository;

    private TrophyDtoMapper trophyDtoMapper;
    private TrophyRepository trophyRepository;

    private final String name = "First Blood";
    private final String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Fighting%20Bronze%20-%20First%20Blood.png";

    @BeforeEach
    void beforeEach() {
        trophyDtoMapper = new TrophyDtoMapper();
        trophyRepository = new TrophyRepository(trophyJpaRepository, trophyDtoMapper);
    }

    @Test
    void testUpsertAndFindAll() {
        Trophy testTrophy = new Trophy(1, name, badgeUrl);
        trophyRepository.upsert(testTrophy);
        ArrayList<Trophy> trophies = trophyRepository.findAll();
        assertThat(trophies).contains(testTrophy);
    }

}

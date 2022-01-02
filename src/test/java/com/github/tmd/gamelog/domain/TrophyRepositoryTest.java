package com.github.tmd.gamelog.domain;

import com.github.tmd.gamelog.adapter.jpa.TrophyJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.TrophyRepository;
import com.github.tmd.gamelog.adapter.jpa.mapper.TrophyDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for class TrophyRepository.
 */
public class TrophyRepositoryTest {

    private TrophyJpaRepository trophyJpaRepository;

    private TrophyDtoMapper trophyDtoMapper;
    private TrophyRepository trophyRepository;

    @BeforeEach
    void beforeEach() {
        trophyDtoMapper = new TrophyDtoMapper();
        trophyRepository = new TrophyRepository(trophyJpaRepository, trophyDtoMapper);
    }

    /**
     * This Test doesn't work because the database connection isn't working atm.
     * TODO: make sure that this test works as soon as database connection works.
     */
    @Test
    void testUpsertAndFindAll() {
        Trophy testTrophy = new Trophy("First Blood");
        trophyRepository.upsert(testTrophy);
        ArrayList<Trophy> trophies = trophyRepository.findAll();
        assertThat(trophies).contains(testTrophy);
    }

}

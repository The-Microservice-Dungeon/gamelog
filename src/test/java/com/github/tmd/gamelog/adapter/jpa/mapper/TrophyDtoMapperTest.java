package com.github.tmd.gamelog.adapter.jpa.mapper;

import com.github.tmd.gamelog.adapter.jpa.dto.TrophyDto;
import com.github.tmd.gamelog.domain.Trophy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the TrophyDtoMapper class.
 */
public class TrophyDtoMapperTest {

    private final long defaultId = 1;
    private final String defaultName = "First Blood";

    private TrophyDtoMapper trophyDtoMapper;

    @BeforeEach
    void beforeEach() {
        trophyDtoMapper = new TrophyDtoMapper();
    }

    @Test
    void testMapEntityToDto() {
        Trophy trophyEntity = new Trophy(defaultId, defaultName);
        TrophyDto trophyDto = trophyDtoMapper.mapEntityToDto(trophyEntity);
        assertThat(trophyDto.getId()).isEqualTo(defaultId);
        assertThat(trophyDto.getName()).isEqualTo(defaultName);
    }

    @Test
    void testMapDtoToEntity() {
        TrophyDto trophyDto = new TrophyDto(defaultId, defaultName);
        Trophy trophy = trophyDtoMapper.mapDtoToEntity(trophyDto);
        assertThat(trophy.getId()).isEqualTo(defaultId);
        assertThat(trophy.getName()).isEqualTo(defaultName);
    }

}

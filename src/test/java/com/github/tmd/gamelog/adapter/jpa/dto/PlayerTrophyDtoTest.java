package com.github.tmd.gamelog.adapter.jpa.dto;

import com.github.tmd.gamelog.domain.trophies.TrophyType;
import com.github.tmd.gamelog.utility.IsoTimestampConverter;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the PlayerTrophyDto class.
 */
class PlayerTrophyDtoTest {

    private final long defaultTrophyId = 1;
    private final String defaultTrophyName = "First Blood";
    private final String defaultTrophyBadgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Fighting%20Bronze%20-%20First%20Blood.png";
    private final UUID defaultPlayerId = UUID.fromString("c6dcbdac-be0b-4de0-b50d-7870caa5f744");
    private UUID gameId = UUID.fromString("d290f1ee-6c54-4b01-90e6-d701748f0851");
    private final long defaultPlayerTrophyDtoId = 1;
    private PlayerTrophyDto playerTrophyDto;
    private TrophyDto trophyDto;
    private PlayerDto playerDto;
    private String isoTimestampStringAwarded;

    @Test
    void newEmptyPlayerTrophyDto() {
        playerTrophyDto = new PlayerTrophyDto();
        assertThat(playerTrophyDto.getId()).isNull();
        assertThat(playerTrophyDto.getTrophyDto()).isNull();
        assertThat(playerTrophyDto.getPlayerDtoAwardedTo()).isNull();
        assertThat(playerTrophyDto.getDateAwarded()).isNull();
    }

    @Test
    void newPlayerTrophyDto() {
        trophyDto = new TrophyDto(defaultTrophyId, defaultTrophyName, defaultTrophyBadgeUrl, TrophyType.Trophy);
        playerDto = new PlayerDto(defaultPlayerId);
        Date dateAwarded = new Date();
        isoTimestampStringAwarded = IsoTimestampConverter.dateToIsoTimestampString(dateAwarded);
        playerTrophyDto = new PlayerTrophyDto(defaultPlayerTrophyDtoId, trophyDto, playerDto, gameId, dateAwarded);
        assertThat(playerTrophyDto.getId()).isEqualTo(defaultPlayerTrophyDtoId);
        assertThat(playerTrophyDto.getTrophyDto()).isEqualTo(new TrophyDto(defaultTrophyId, defaultTrophyName, defaultTrophyBadgeUrl, TrophyType.Trophy));
        assertThat(playerTrophyDto.getPlayerDtoAwardedTo()).isEqualTo(new PlayerDto(defaultPlayerId));
        assertThat(playerTrophyDto.getDateAwarded()).isEqualTo(IsoTimestampConverter.dateFromIsoTimestampString(isoTimestampStringAwarded));
    }

}
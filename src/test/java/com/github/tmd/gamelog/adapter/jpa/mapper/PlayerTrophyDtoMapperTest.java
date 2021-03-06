package com.github.tmd.gamelog.adapter.jpa.mapper;

import com.github.tmd.gamelog.adapter.jpa.dto.PlayerTrophyDto;
import com.github.tmd.gamelog.adapter.jpa.dto.TrophyDto;
import com.github.tmd.gamelog.domain.PlayerTrophy;
import com.github.tmd.gamelog.domain.trophies.Trophy;
import com.github.tmd.gamelog.domain.trophies.TrophyType;
import com.github.tmd.gamelog.utility.IsoTimestampConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the PlayerTrophyDtoMapper class.
 */
public class PlayerTrophyDtoMapperTest {

    private final long trophyId = 1;
    private final String trophyName = "First Blood";
    private final String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Fighting%20Bronze%20-%20First%20Blood.png";
    private PlayerTrophyDtoMapper playerTrophyDtoMapper;
    private String isoTimestampStringAwarded;
    private UUID gameId = UUID.fromString("d290f1ee-6c54-4b01-90e6-d701748f0851");

    @BeforeEach
    void beforeEach() {
        playerTrophyDtoMapper = new PlayerTrophyDtoMapper();
    }

    @Test
    void testMapEntityToDto() {
        PlayerTrophy playerTrophy = setupPlayerTrophy();
        PlayerTrophyDto playerTrophyDto = playerTrophyDtoMapper.mapEntityToDto(playerTrophy);
        assertThat(playerTrophyDto.getTrophyDto()).isEqualTo(new TrophyDto(trophyId, trophyName, badgeUrl, TrophyType.Trophy));
        assertThat(playerTrophyDto.getGameId()).isEqualTo(gameId);
        assertThat(playerTrophyDto.getDateAwarded()).isEqualTo(IsoTimestampConverter.dateFromIsoTimestampString(isoTimestampStringAwarded));
    }

    private PlayerTrophy setupPlayerTrophy() {
        Trophy trophy = new Trophy(trophyId, trophyName, badgeUrl);
        Date dateAwarded = new Date();
        isoTimestampStringAwarded = IsoTimestampConverter.dateToIsoTimestampString(dateAwarded);
        PlayerTrophy playerTrophy = new PlayerTrophy(trophy, gameId, dateAwarded);
        return playerTrophy;
    }

    @Test
    void testMapDtoToEntity() {
        PlayerTrophyDto playerTrophyDto = setupPlayerTrophyDto();
        PlayerTrophy playerTrophy = playerTrophyDtoMapper.mapDtoToEntity(playerTrophyDto);
        assertThat(playerTrophy.getTrophy()).isEqualTo(new Trophy(trophyId, trophyName, badgeUrl));
        assertThat(playerTrophy.getGameId()).isEqualTo(gameId);
        assertThat(playerTrophy.getDateAwarded()).isEqualTo(IsoTimestampConverter.dateFromIsoTimestampString(isoTimestampStringAwarded));
    }

    private PlayerTrophyDto setupPlayerTrophyDto() {
        TrophyDto trophyDto = new TrophyDto(trophyId, trophyName, badgeUrl, TrophyType.Trophy);
        Date dateAwarded = new Date();
        isoTimestampStringAwarded = IsoTimestampConverter.dateToIsoTimestampString(dateAwarded);
        PlayerTrophyDto playerTrophyDto = new PlayerTrophyDto(trophyId, trophyDto, gameId, dateAwarded);
        return playerTrophyDto;
    }

}

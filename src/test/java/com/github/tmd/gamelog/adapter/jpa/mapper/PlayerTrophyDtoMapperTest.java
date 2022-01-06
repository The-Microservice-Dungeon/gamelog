package com.github.tmd.gamelog.adapter.jpa.mapper;

import com.github.tmd.gamelog.adapter.jpa.dto.PlayerDto;
import com.github.tmd.gamelog.adapter.jpa.dto.PlayerTrophyDto;
import com.github.tmd.gamelog.adapter.jpa.dto.TrophyDto;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.PlayerTrophy;
import com.github.tmd.gamelog.domain.Trophy;
import com.github.tmd.gamelog.utility.IsoTimestampConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the PlayerTrophyDtoMapper class.
 */
public class PlayerTrophyDtoMapperTest {

    private final String playerId = "c6dcbdac-be0b-4de0-b50d-7870caa5f744";
    private final long trophyId = 1;
    private final String trophyName = "First Blood";
    private final String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Fighting%20Bronze%20-%20First%20Blood.png";
    private PlayerTrophyDtoMapper playerTrophyDtoMapper;
    private String isoTimestampStringAwarded;

    @BeforeEach
    void beforeEach() {
        playerTrophyDtoMapper = new PlayerTrophyDtoMapper();
    }

    @Test
    void testMapEntityToDto() {
        PlayerTrophy playerTrophy = setupPlayerTrophy();
        PlayerTrophyDto playerTrophyDto = playerTrophyDtoMapper.mapEntityToDto(playerTrophy);
        assertThat(playerTrophyDto.getPlayerDtoAwardedTo()).isEqualTo(new PlayerDto(playerId));
        assertThat(playerTrophyDto.getTrophyDto()).isEqualTo(new TrophyDto(trophyId, trophyName, badgeUrl));
        assertThat(playerTrophyDto.getDateAwarded()).isEqualTo(IsoTimestampConverter.dateFromIsoTimestampString(isoTimestampStringAwarded));
    }

    private PlayerTrophy setupPlayerTrophy() {
        Player player = new Player(playerId);
        Trophy trophy = new Trophy(trophyId, trophyName, badgeUrl);
        Date dateAwarded = new Date();
        isoTimestampStringAwarded = IsoTimestampConverter.dateToIsoTimestampString(dateAwarded);
        PlayerTrophy playerTrophy = new PlayerTrophy(player, trophy, dateAwarded);
        return playerTrophy;
    }

    @Test
    void testMapDtoToEntity() {
        PlayerTrophyDto playerTrophyDto = setupPlayerTrophyDto();
        PlayerTrophy playerTrophy = playerTrophyDtoMapper.mapDtoToEntity(playerTrophyDto);
        assertThat(playerTrophy.getPlayerAwardedTo()).isEqualTo(new Player(playerId));
        assertThat(playerTrophy.getTrophy()).isEqualTo(new Trophy(trophyId, trophyName, badgeUrl));
        assertThat(playerTrophy.getDateAwarded()).isEqualTo(IsoTimestampConverter.dateFromIsoTimestampString(isoTimestampStringAwarded));
    }

    private PlayerTrophyDto setupPlayerTrophyDto() {
        PlayerDto playerDto = new PlayerDto(playerId);
        TrophyDto trophyDto = new TrophyDto(trophyId, trophyName, badgeUrl);
        Date dateAwarded = new Date();
        isoTimestampStringAwarded = IsoTimestampConverter.dateToIsoTimestampString(dateAwarded);
        PlayerTrophyDto playerTrophyDto = new PlayerTrophyDto(trophyId, trophyDto, playerDto, dateAwarded);
        return playerTrophyDto;
    }

}

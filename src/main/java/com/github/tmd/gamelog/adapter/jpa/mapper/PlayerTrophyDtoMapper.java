package com.github.tmd.gamelog.adapter.jpa.mapper;

import com.github.tmd.gamelog.adapter.jpa.dto.PlayerTrophyDto;
import com.github.tmd.gamelog.adapter.jpa.dto.TrophyDto;
import com.github.tmd.gamelog.domain.PlayerTrophy;
import com.github.tmd.gamelog.domain.trophies.Trophy;
import org.springframework.stereotype.Component;

/**
 * Class for mapping PlayerTrophyDto to PlayerTrophy and vice-versa.
 */
@Component
public class PlayerTrophyDtoMapper {

    public PlayerTrophyDto mapEntityToDto(PlayerTrophy playerTrophy) {
        PlayerTrophyDto playerTrophyDto = new PlayerTrophyDto();
        playerTrophyDto.setTrophyDto(trophyToTrophyDto(playerTrophy.getTrophy()));
        playerTrophyDto.setGameId(playerTrophy.getGameId());
        playerTrophyDto.setDateAwarded(playerTrophy.getDateAwarded());
        return playerTrophyDto;
    }

    public PlayerTrophy mapDtoToEntity(PlayerTrophyDto playerTrophyDto) {
        PlayerTrophy playerTrophy = new PlayerTrophy();
        playerTrophy.setTrophy(trophyDtoToTrophy(playerTrophyDto.getTrophyDto()));
        playerTrophy.setGameId(playerTrophyDto.getGameId());
        playerTrophy.setDateAwarded(playerTrophyDto.getDateAwarded());
        return playerTrophy;
    }

    private Trophy trophyDtoToTrophy(TrophyDto trophyDto) {
        TrophyDtoMapper trophyDtoMapper = new TrophyDtoMapper();
        Trophy trophy = trophyDtoMapper.mapDtoToEntity(trophyDto);
        return trophy;
    }

    private TrophyDto trophyToTrophyDto(Trophy trophy) {
        TrophyDtoMapper trophyDtoMapper = new TrophyDtoMapper();
        TrophyDto trophyDto = trophyDtoMapper.mapEntityToDto(trophy);
        return trophyDto;
    }
}

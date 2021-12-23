package com.github.tmd.gamelog.adapter.jpa.mapper;

import com.github.tmd.gamelog.adapter.jpa.dto.PlayerDto;
import com.github.tmd.gamelog.adapter.jpa.dto.PlayerTrophyDto;
import com.github.tmd.gamelog.adapter.jpa.dto.TrophyDto;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.PlayerTrophy;
import com.github.tmd.gamelog.domain.Trophy;
import org.springframework.stereotype.Component;

/**
 * Class for mapping PlayerTrophyDto to PlayerTrophy and vice-versa.
 */
@Component
public class PlayerTrophyDtoMapper {

    public PlayerTrophyDto mapEntityToDto(PlayerTrophy playerTrophy) {
        PlayerTrophyDto playerTrophyDto = new PlayerTrophyDto();
        playerTrophyDto.setPlayerDtoAwardedTo(playerToPlayerDto(playerTrophy.getPlayerAwardedTo()));
        playerTrophyDto.setTrophyDto(trophyToTrophyDto(playerTrophy.getTrophy()));
        playerTrophyDto.setDateAwarded(playerTrophy.getDateAwarded());
        return playerTrophyDto;
    }

    public PlayerTrophy mapDtoToEntity(PlayerTrophyDto playerTrophyDto) {
        PlayerTrophy playerTrophy = new PlayerTrophy();
        playerTrophy.setPlayerAwardedTo(playerDtoToPlayer(playerTrophyDto.getPlayerDtoAwardedTo()));
        playerTrophy.setTrophy(trophyDtoToTrophy(playerTrophyDto.getTrophyDto()));
        playerTrophy.setDateAwarded(playerTrophyDto.getDateAwarded());
        return playerTrophy;
    }

    private Trophy trophyDtoToTrophy(TrophyDto trophyDto) {
        TrophyDtoMapper trophyDtoMapper = new TrophyDtoMapper();
        Trophy trophy = trophyDtoMapper.mapDtoToEntity(trophyDto);
        return trophy;
    }

    private Player playerDtoToPlayer(PlayerDto playerDto) {
        PlayerDtoMapper playerDtoMapper = new PlayerDtoMapper();
        Player player = playerDtoMapper.mapDtoToEntity(playerDto);
        return player;
    }

    private TrophyDto trophyToTrophyDto(Trophy trophy) {
        TrophyDtoMapper trophyDtoMapper = new TrophyDtoMapper();
        TrophyDto trophyDto = trophyDtoMapper.mapEntityToDto(trophy);
        return trophyDto;
    }

    private PlayerDto playerToPlayerDto(Player player) {
        PlayerDtoMapper playerDtoMapper = new PlayerDtoMapper();
        PlayerDto playerDto = playerDtoMapper.mapEntityToDto(player);
        return playerDto;
    }
}

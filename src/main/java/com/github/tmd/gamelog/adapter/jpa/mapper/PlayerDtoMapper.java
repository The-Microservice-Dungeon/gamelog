package com.github.tmd.gamelog.adapter.jpa.mapper;

import com.github.tmd.gamelog.adapter.jpa.dto.PlayerDto;
import com.github.tmd.gamelog.adapter.jpa.dto.PlayerTrophyDto;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.PlayerTrophy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Class for mapping PlayerDto to Player and vice-versa.
 */
@Component
public class PlayerDtoMapper {

    private PlayerTrophyDtoMapper playerTrophyDtoMapper;

    public PlayerDtoMapper() {
        playerTrophyDtoMapper = new PlayerTrophyDtoMapper();
    }

    PlayerDto mapEntityToDto(Player player) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(player.getId());
        playerDto.setEarnedTrophies(mapTrophiesToTrophyDtos(player.getEarnedTrophies()));
        return playerDto;
    }

    Player mapDtoToEntity(PlayerDto playerDto) {
        Player player = new Player();
        player.setId(playerDto.getId());
        player.setEarnedTrophies(mapTrophyDtosToTrophies(playerDto.getEarnedTrophies()));
        return player;
    }

    private Set<PlayerTrophyDto> mapTrophiesToTrophyDtos(Set<PlayerTrophy> playerTrophies) {
        Set<PlayerTrophyDto> playerTrophyDtos = new HashSet<>();
        for (PlayerTrophy playerTrophy : playerTrophies) {
            playerTrophyDtos.add(playerTrophyDtoMapper.mapEntityToDto(playerTrophy));
        }
        return playerTrophyDtos;
    }

    private Set<PlayerTrophy> mapTrophyDtosToTrophies(Set<PlayerTrophyDto> playerTrophyDtos) {
        Set<PlayerTrophy> playerTrophies = new HashSet<>();
        for (PlayerTrophyDto playerTrophyDto : playerTrophyDtos) {
            playerTrophies.add(playerTrophyDtoMapper.mapDtoToEntity(playerTrophyDto));
        }
        return playerTrophies;
    }

}

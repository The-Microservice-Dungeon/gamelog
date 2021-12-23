package com.github.tmd.gamelog.adapter.jpa.mapper;

import com.github.tmd.gamelog.adapter.jpa.dto.PlayerDto;
import com.github.tmd.gamelog.domain.Player;
import org.springframework.stereotype.Component;

/**
 * Class for mapping PlayerDto to Player and vice-versa.
 */
@Component
public class PlayerDtoMapper {

    PlayerDto mapEntityToDto(Player player) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(player.getId());
        return playerDto;
    }

    Player mapDtoToEntity(PlayerDto playerDto) {
        Player player = new Player();
        player.setId(playerDto.getId());
        return player;
    }

}

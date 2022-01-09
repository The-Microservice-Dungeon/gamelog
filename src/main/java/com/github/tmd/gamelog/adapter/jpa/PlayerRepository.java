package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.adapter.jpa.dto.PlayerDto;
import com.github.tmd.gamelog.adapter.jpa.mapper.PlayerDtoMapper;
import com.github.tmd.gamelog.domain.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for objects of class Player.
 */
public class PlayerRepository {

    private final PlayerJpaRepository playerJpaRepository;
    private final PlayerDtoMapper playerDtoMapper;

    public PlayerRepository(PlayerJpaRepository playerJpaRepository, PlayerDtoMapper playerDtoMapper) {
        this.playerJpaRepository = playerJpaRepository;
        this.playerDtoMapper = playerDtoMapper;
    }

    public ArrayList<Player> findAll() {
        List<PlayerDto> playerDtos = playerJpaRepository.findAll();
        ArrayList<Player> players = new ArrayList<>();
        for (PlayerDto playerDto : playerDtos) {
            players.add(playerDtoMapper.mapDtoToEntity(playerDto));
        }
        return players;
    }

    public void upsert(Player player) {
        PlayerDto playerDto = playerDtoMapper.mapEntityToDto(player);
        playerJpaRepository.save(playerDto);
    }

}

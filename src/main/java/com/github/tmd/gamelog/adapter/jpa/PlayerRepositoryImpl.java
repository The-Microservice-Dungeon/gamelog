package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.adapter.jpa.dto.PlayerDto;
import com.github.tmd.gamelog.adapter.jpa.mapper.PlayerDtoMapper;
import com.github.tmd.gamelog.domain.Player;

import com.github.tmd.gamelog.domain.PlayerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

/**
 * Repository for objects of class Player.
 */
@Repository
public class PlayerRepositoryImpl implements PlayerRepository {

    private final PlayerJpaRepository playerJpaRepository;
    private final PlayerDtoMapper playerDtoMapper;

    public PlayerRepositoryImpl(PlayerJpaRepository playerJpaRepository, PlayerDtoMapper playerDtoMapper) {
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

    public Optional<Player> findById(UUID playerId) {
        return playerJpaRepository.findById(playerId)
            .map(playerDtoMapper::mapDtoToEntity);
    }

    public void upsert(Player player) {
        PlayerDto playerDto = playerDtoMapper.mapEntityToDto(player);
        playerJpaRepository.save(playerDto);
    }

}

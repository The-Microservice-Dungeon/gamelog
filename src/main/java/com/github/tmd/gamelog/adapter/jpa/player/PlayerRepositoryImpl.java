package com.github.tmd.gamelog.adapter.jpa.player;

import com.github.tmd.gamelog.domain.player.Player;
import com.github.tmd.gamelog.domain.player.PlayerRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerRepositoryImpl implements PlayerRepository {
  private final PlayerJpaRepository playerJpaRepository;
  private final PlayerMapper playerMapper;

  public PlayerRepositoryImpl(
      PlayerJpaRepository playerJpaRepository,
      PlayerMapper playerMapper) {
    this.playerJpaRepository = playerJpaRepository;
    this.playerMapper = playerMapper;
  }

  @Override
  public Player save(Player player) {
    var jpa = playerMapper.toPersistence(player);
    var saved = this.playerJpaRepository.save(jpa);
    return playerMapper.toDomain(saved);
  }

  @Override
  public Optional<Player> findById(UUID playerId) {
    return this.playerJpaRepository.findById(playerId)
        .map(this.playerMapper::toDomain);
  }
}

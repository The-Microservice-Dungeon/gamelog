package com.github.tmd.gamelog.application;

import com.github.tmd.gamelog.adapter.rest.client.GameRestClient;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.PlayerRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
  private final PlayerRepository playerRepository;
  private final GameRestClient gameRestClient;

  public PlayerService(PlayerRepository playerRepository,
      GameRestClient gameRestClient) {
    this.playerRepository = playerRepository;
    this.gameRestClient = gameRestClient;
  }

  public void createOrUpdatePlayer(Player player) {
    createOrUpdatePlayer(player.getId(), player.getName());
  }

  public void createOrUpdatePlayer(UUID playerId, String name) {
    var playerToSave = this.playerRepository.findById(playerId)
        .map(player -> {
          player.setName(name);
          return player;
        })
        .orElse(new Player(playerId, name));

    this.playerRepository.upsert(playerToSave);
  }

  public Optional<Player> findPlayerById(UUID playerId) {
    return this.playerRepository.findById(playerId);
  }

  public Set<Player> findParticipatingPlayersInGame(UUID gameId) {
    return this.gameRestClient.getParticipatingPlayers(gameId)
        .gamePlayersWrapper().players()
        .stream().map(entry -> new Player(entry.playerToken(), entry.userName()))
        .collect(Collectors.toSet());
  }
}

package com.github.tmd.gamelog.domain;

public interface RoundScoreRepository {
    public RoundScore findByGameAndRoundAndPlayer(String gameId, String roundId, String playerId);

    public void save(RoundScore roundScore);
}

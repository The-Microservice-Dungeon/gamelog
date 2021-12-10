package com.github.tmd.gamelog.domain;

import com.github.tmd.gamelog.adapter.jpa.RoundScoreDto;

public interface RoundScoreRepository {
    public RoundScore findByGameAndRoundAndPlayer(String gameId, String roundId, String playerId);

    public void save(RoundScore roundScore);
}

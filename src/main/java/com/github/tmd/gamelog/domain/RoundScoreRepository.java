package com.github.tmd.gamelog.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RoundScoreRepository {
    RoundScore findByCommandContext(CommandContext commandContext);

    void upsert(RoundScore roundScore);

    ArrayList<RoundScore> findAllByRoundId(String roundId);
}

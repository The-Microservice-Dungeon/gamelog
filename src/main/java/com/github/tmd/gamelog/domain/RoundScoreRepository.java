package com.github.tmd.gamelog.domain;

import java.util.ArrayList;

public interface RoundScoreRepository {
    RoundScore findByCommandContext(CommandContext commandContext);

    void upsert(RoundScore roundScore);

    ArrayList<RoundScore> findAllByRoundId(String roundId);
}

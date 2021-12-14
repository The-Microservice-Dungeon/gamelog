package com.github.tmd.gamelog.domain;

public interface RoundScoreRepository {
    public RoundScore findByCommandContext(CommandContext commandContext);

    public void save(RoundScore roundScore);
}

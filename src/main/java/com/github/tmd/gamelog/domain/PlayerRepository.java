package com.github.tmd.gamelog.domain;

public interface PlayerRepository {
    public Player findByRobotId(String robotId);
}

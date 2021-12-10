package com.github.tmd.gamelog.domain;

import org.springframework.stereotype.Repository;

public interface PlayerRepository {
    public Player findByRobotId(String robotId);
}

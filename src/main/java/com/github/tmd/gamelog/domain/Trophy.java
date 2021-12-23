package com.github.tmd.gamelog.domain;

import lombok.Data;

/**
 * Data class for Trophies.
 * Trophies have a name.
 * They are awarded to players for achieving certain goals in-game.
 */
@Data
public class Trophy {

    private String name;

    public Trophy(String name) {
        this.name = name;
    }
}

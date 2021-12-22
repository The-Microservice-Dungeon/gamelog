package com.github.tmd.gamelog.domain;

import lombok.Data;

/**
 * Data class for Trophies.
 * Trophies contain an id and a name.
 * They are awarded to players for achieving certain goals in-game.
 */
@Data
public class Trophy {

    private int id;
    private String name;

    public Trophy(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

package com.github.tmd.gamelog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data class for Trophies.
 * Trophies have a name.
 * They are awarded to players for achieving certain goals in-game.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trophy {

    private long id;
    private String name;

    public Trophy(String name) {
        this.name = name;
    }
}

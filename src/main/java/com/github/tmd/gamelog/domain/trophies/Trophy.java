package com.github.tmd.gamelog.domain.trophies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data class for Trophies.
 * Trophies have a name and a badge url which points to the graphics file that contains the trophy badge.
 * They are awarded to players for achieving certain goals in-game.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trophy {

    private long id;
    private String name = "";
    private String badgeUrl = "";

    public Trophy(String name, String badgeUrl) {
        this.name = name;
        this.badgeUrl = badgeUrl;
    }
}

package com.github.tmd.gamelog.domain.trophies;

import com.github.tmd.gamelog.domain.trophies.achievements.*;
import com.github.tmd.gamelog.domain.trophies.scoreboard.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

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

    /**
     * Get the list of default trophies.
     * Each trophy is an object of a different subclass of Trophy.
     *
     * @return List of the default trophies.
     */
    public static ArrayList<Trophy> getDefaultTrophies() {
        ArrayList<Trophy> requiredTrophies = new ArrayList<>();
        requiredTrophies.add(new FightingBronzeTrophy());
        requiredTrophies.add(new FightingSilverTrophy());
        requiredTrophies.add(new FightingGoldTrophy());
        requiredTrophies.add(new MiningBronzeTrophy());
        requiredTrophies.add(new MiningSilverTrophy());
        requiredTrophies.add(new MiningGoldTrophy());
        requiredTrophies.add(new TradingBronzeTrophy());
        requiredTrophies.add(new TradingSilverTrophy());
        requiredTrophies.add(new TradingGoldTrophy());
        requiredTrophies.add(new TravelingBronzeTrophy());
        requiredTrophies.add(new TravelingSilverTrophy());
        requiredTrophies.add(new TravelingGoldTrophy());

        requiredTrophies.add(new GameThirdPlaceTrophy());
        requiredTrophies.add(new GameSecondPlaceTrophy());
        requiredTrophies.add(new GameFirstPlaceTrophy());
        requiredTrophies.add(new FightingThirdPlaceTrophy());
        requiredTrophies.add(new FightingSecondPlaceTrophy());
        requiredTrophies.add(new FightingFirstPlaceTrophy());
        requiredTrophies.add(new MiningThirdPlaceTrophy());
        requiredTrophies.add(new MiningSecondPlaceTrophy());
        requiredTrophies.add(new MiningFirstPlaceTrophy());
        requiredTrophies.add(new TradingThirdPlaceTrophy());
        requiredTrophies.add(new TradingSecondPlaceTrophy());
        requiredTrophies.add(new TradingFirstPlaceTrophy());
        requiredTrophies.add(new TravelingThirdPlaceTrophy());
        requiredTrophies.add(new TravelingSecondPlaceTrophy());
        requiredTrophies.add(new TravelingFirstPlaceTrophy());
        return requiredTrophies;
    }
}

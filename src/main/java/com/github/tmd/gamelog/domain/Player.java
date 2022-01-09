package com.github.tmd.gamelog.domain;

import com.github.tmd.gamelog.domain.trophies.Trophy;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Player {
    private UUID id;
    private Set<PlayerTrophy> earnedTrophies;

    public Player() {
        this.id = null;
        this.earnedTrophies = new HashSet<PlayerTrophy>();
    }

    public Player(UUID id) {
        this.id = id;
        this.earnedTrophies = new HashSet<PlayerTrophy>();
    }

    /**
     * Check if the player has already earned this trophy in the game referenced by gameId.
     *
     * @param trophy Trophy the player may earn.
     * @param gameId ID of the game.
     * @return True if the player has already earned this trophy in the game. Else false.
     */
    public Boolean trophyAlreadyEarned(Trophy trophy, UUID gameId) {
        for (PlayerTrophy playerTrophy : earnedTrophies) {
            if (playerTrophy.getTrophy() == trophy && playerTrophy.getGameId() == gameId) return true;
        }
        return false;
    }

    /**
     * Award a trophy to the player for the game referenced by gameId.
     * Only awards the trophy if it hasn't already been earned in the game.
     *
     * @param trophy Trophy the player should earn.
     * @param gameId ID of the game.
     */
    public void awardTrophy(Trophy trophy, UUID gameId) {
        if (!trophyAlreadyEarned(trophy, gameId)) {
            PlayerTrophy playerTrophy = new PlayerTrophy(trophy, gameId, new Date());
            earnedTrophies.add(playerTrophy);
        }
    }

}

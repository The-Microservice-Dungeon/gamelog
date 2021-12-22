package com.github.tmd.gamelog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Data class for the players trophies.
 * Contains a trophy, a reference to the player it has been awarded to and
 * the date it was awarded to the player.
 */
@Data
@AllArgsConstructor
public class PlayerTrophy {

    private Player awardedToPlayer;
    private Trophy trophy;
    private Date dateAwarded;

}

package com.github.tmd.gamelog.domain;

import com.github.tmd.gamelog.domain.trophies.Trophy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Data class for the players trophies.
 * Contains a trophy, a reference to the player it has been awarded to and
 * the date it was awarded to the player.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerTrophy {

    private Player playerAwardedTo;
    private Trophy trophy;
    private Date dateAwarded;

}

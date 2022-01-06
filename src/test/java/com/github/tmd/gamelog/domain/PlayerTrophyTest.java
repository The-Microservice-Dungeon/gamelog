package com.github.tmd.gamelog.domain;

import com.github.tmd.gamelog.utility.IsoTimestampConverter;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the PlayerTrophy class.
 */
public class PlayerTrophyTest {

    private final String playerId = "c6dcbdac-be0b-4de0-b50d-7870caa5f744";
    private final String trophyName = "First Blood";
    private final String trophyBadgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Fighting%20Bronze%20-%20First%20Blood.png";
    private PlayerTrophy playerTrophy;
    private String isoTimestampStringAwarded;

    @Test
    void testEmptyNewPlayerTrophy() {
        PlayerTrophy playerTrophy = new PlayerTrophy();
        assertThat(playerTrophy.getPlayerAwardedTo()).isNull();
        assertThat(playerTrophy.getTrophy()).isNull();
        assertThat(playerTrophy.getDateAwarded()).isNull();
    }

    @Test
    void testNewPlayerTrophy() {
        Player player = new Player(playerId);
        Trophy trophy = new Trophy(trophyName, trophyBadgeUrl);
        Date dateAwarded = new Date();
        isoTimestampStringAwarded = IsoTimestampConverter.dateToIsoTimestampString(dateAwarded);
        playerTrophy = new PlayerTrophy(player, trophy, dateAwarded);
        assertThat(playerTrophy.getPlayerAwardedTo()).isEqualTo(new Player(playerId));
        assertThat(playerTrophy.getTrophy()).isEqualTo(new Trophy(trophyName, trophyBadgeUrl));
        assertThat(playerTrophy.getDateAwarded()).isEqualTo(IsoTimestampConverter.dateFromIsoTimestampString(isoTimestampStringAwarded));
    }

}

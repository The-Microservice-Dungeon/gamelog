package com.github.tmd.gamelog.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the PlayerTrophy class.
 */
public class PlayerTrophyTest {

    PlayerTrophy playerTrophy;

    private final String playerId = "c6dcbdac-be0b-4de0-b50d-7870caa5f744";
    private final int trophyId = 1;
    private final String trophyName = "First Blood";
    private String isoTimestampStringAwarded;

    @BeforeEach
    void beforeEach() {
        Player player = new Player(playerId);
        Trophy trophy = new Trophy(trophyId, trophyName);
        Date dateAwarded = new Date();
        isoTimestampStringAwarded = dateToIsoTimestampString(dateAwarded);
        playerTrophy = new PlayerTrophy(player, trophy, dateAwarded);
    }

    @Test
    void testNewPlayerTrophy() {
        assertThat(playerTrophy.getAwardedToPlayer()).isEqualTo(new Player(playerId));
        assertThat(playerTrophy.getTrophy()).isEqualTo(new Trophy(trophyId, trophyName));
        assertThat(playerTrophy.getDateAwarded()).isEqualTo(dateFromIsoTimestampString(isoTimestampStringAwarded));
    }

    private String dateToIsoTimestampString(Date date) {
        return date.toInstant().toString();
    }
    
    private Date dateFromIsoTimestampString(String isoTimestampAsString){
        return Date.from(Instant.parse(isoTimestampAsString));
    }

}

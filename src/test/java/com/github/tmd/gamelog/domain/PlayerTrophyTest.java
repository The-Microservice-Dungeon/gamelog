package com.github.tmd.gamelog.domain;

import com.github.tmd.gamelog.domain.trophies.Trophy;
import com.github.tmd.gamelog.utility.IsoTimestampConverter;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the PlayerTrophy class.
 */
public class PlayerTrophyTest {

    private final UUID playerId = UUID.fromString("c6dcbdac-be0b-4de0-b50d-7870caa5f744");
    private final String trophyName = "First Blood";
    private final String trophyBadgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Fighting%20Bronze%20-%20First%20Blood.png";
    private PlayerTrophy playerTrophy;
    private UUID gameId = UUID.fromString("d290f1ee-6c54-4b01-90e6-d701748f0851");
    private String isoTimestampStringAwarded;

    @Test
    void testEmptyNewPlayerTrophy() {
        PlayerTrophy playerTrophy = new PlayerTrophy();
        assertThat(playerTrophy.getPlayerAwardedTo()).isNull();
        assertThat(playerTrophy.getTrophy()).isNull();
        assertThat(playerTrophy.getGameId()).isNull();
        assertThat(playerTrophy.getDateAwarded()).isNull();
    }

    @Test
    void testNewPlayerTrophy() {
        Player player = new Player(playerId);
        Trophy trophy = new Trophy(trophyName, trophyBadgeUrl);
        Date dateAwarded = new Date();
        isoTimestampStringAwarded = IsoTimestampConverter.dateToIsoTimestampString(dateAwarded);
        playerTrophy = new PlayerTrophy(player, trophy, gameId, dateAwarded);
        assertThat(playerTrophy.getPlayerAwardedTo()).isEqualTo(new Player(playerId));
        assertThat(playerTrophy.getTrophy()).isEqualTo(new Trophy(trophyName, trophyBadgeUrl));
        assertThat(playerTrophy.getGameId()).isEqualTo(gameId);
        assertThat(playerTrophy.getDateAwarded()).isEqualTo(IsoTimestampConverter.dateFromIsoTimestampString(isoTimestampStringAwarded));
    }

}

package com.github.tmd.gamelog.adapter.event.gameEvent;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tmd.gamelog.domain.RoundScore;
import org.junit.jupiter.api.Test;

public class MovementScoreUnitTest {

    @Test
    void testMovementExecute() {
        RoundScore roundScore = new RoundScore();
        assertThat(roundScore.getMovementScore().getValue()).isEqualTo(0);

        MovementEvent movementEvent = new MovementEvent();
        movementEvent.execute(roundScore);

        assertThat(roundScore.getMovementScore().getValue()).isEqualTo(1);
    }
}

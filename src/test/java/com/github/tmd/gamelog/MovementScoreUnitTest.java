package com.github.tmd.gamelog;

import com.github.tmd.gamelog.adapter.event.gameEvent.MovementEvent;
import com.github.tmd.gamelog.domain.RoundScore;
import org.junit.jupiter.api.Test;

public class MovementScoreUnitTest {

    @Test
    void testMovementExecute() {
        RoundScore roundScore = new RoundScore();
        assert roundScore.getMovementScore() == 0;
        MovementEvent movementEvent = new MovementEvent();
        movementEvent.execute(roundScore);
        assert roundScore.getMovementScore() == 1;
    }
}
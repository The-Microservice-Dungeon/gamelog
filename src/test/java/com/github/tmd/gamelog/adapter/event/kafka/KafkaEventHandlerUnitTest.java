package com.github.tmd.gamelog.adapter.event.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tmd.gamelog.adapter.jpa.RoundScoreRepository;
import com.github.tmd.gamelog.domain.CommandContext;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.Round;
import com.github.tmd.gamelog.domain.RoundScore;
import com.github.tmd.gamelog.domain.Score.MovementScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class KafkaEventHandlerUnitTest {

    KafkaEventHandler kafkaEventHandler;

    @Mock
    RoundScoreRepository roundScoreRepository;

    private UUID playerId = UUID.fromString("c6dcbdac-be0b-4de0-b50d-7870caa5f744");

    @BeforeEach
    void init() {
        this.kafkaEventHandler = new KafkaEventHandler(roundScoreRepository);
    }

    @Test
    void testHandleEvent() {
        KafkaEvent kafkaEvent = new KafkaEvent();
        kafkaEvent.setType("movement");
        kafkaEvent.setPayload("{}");
        CommandContext commandContext = this.createGameContext();

        RoundScore roundScore = new RoundScore();
        roundScore.setRound(
            new Round("0",0,"1")
        );
        roundScore.setPlayer(
            new Player(playerId)
        );
        MovementScore movementScore = new MovementScore();
        movementScore.increase(1);
        roundScore.setMovementScore(movementScore);

        Mockito.when(roundScoreRepository.findByCommandContext(commandContext)).thenReturn(roundScore);

        kafkaEventHandler.handleEvent(kafkaEvent, commandContext);

        assertThat(roundScore.getMovementScore().getValue()).isEqualTo(2);
    }

    // @todo redundant siehe RoundScoreRepositoryTest
    private CommandContext createGameContext() {
        CommandContext commandContext = new CommandContext();
        commandContext.setPlayer(new Player(playerId));
        commandContext.setRound(new Round("1", 0, "2"));

        return commandContext;
    }
}

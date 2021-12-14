package com.github.tmd.gamelog.adapter.event.kafka;

import com.github.tmd.gamelog.adapter.jpa.RoundScoreRepository;
import com.github.tmd.gamelog.domain.CommandContext;
import com.github.tmd.gamelog.domain.RoundScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class KafkaEventHandlerUnitTest {

    KafkaEventHandler kafkaEventHandler;

    @Mock
    RoundScoreRepository roundScoreRepository;

    @BeforeEach
    void init() {
        this.kafkaEventHandler = new KafkaEventHandler(roundScoreRepository);
    }

    @Test
    void testHandleEvent() {
        KafkaEvent kafkaEvent = new KafkaEvent();
        kafkaEvent.setType("movement");
        kafkaEvent.setPayload("{}");
        CommandContext commandContext = new CommandContext();
        commandContext.setGameId("1");
        commandContext.setRoundId("2");
        commandContext.setPlayerId("3");

        RoundScore roundScore = new RoundScore();
        roundScore.setGame("1");
        roundScore.setRound("2");
        roundScore.setPlayer("3");
        roundScore.setMovementScore(1);

        Mockito.when(roundScoreRepository.findByCommandContext(commandContext)).thenReturn(roundScore);

        kafkaEventHandler.handleEvent(kafkaEvent, commandContext);

        assert roundScore.getMovementScore() == 2;
    }

}

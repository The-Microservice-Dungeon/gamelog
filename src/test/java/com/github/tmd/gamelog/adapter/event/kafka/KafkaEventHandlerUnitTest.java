package com.github.tmd.gamelog.adapter.event.kafka;

import com.github.tmd.gamelog.adapter.jpa.RoundScoreRepository;
import com.github.tmd.gamelog.domain.CommandContext;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.Round;
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
        CommandContext commandContext = this.createGameContext();

        RoundScore roundScore = new RoundScore();
        roundScore.setGame("1");
        roundScore.setRound("2");
        roundScore.setPlayer("3");
        roundScore.setMovementScore(1);

        Mockito.when(roundScoreRepository.findByCommandContext(commandContext)).thenReturn(roundScore);

        kafkaEventHandler.handleEvent(kafkaEvent, commandContext);

        assert roundScore.getMovementScore() == 2;
    }

    // @todo redundant siehe RoundScoreRepositoryTest
    private CommandContext createGameContext() {
        CommandContext commandContext = new CommandContext();
        commandContext.setPlayer(new Player("3"));
        commandContext.setRound(new Round("1", 0, "2"));

        return commandContext;
    }
}

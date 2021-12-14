package com.github.tmd.gamelog.adapter.event.kafka;

import com.github.tmd.gamelog.adapter.jpa.RoundScoreDto;
import com.github.tmd.gamelog.adapter.jpa.RoundScoreJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.RoundScoreRepository;
import com.github.tmd.gamelog.domain.CommandContext;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.Round;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.invocation.Invocation;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collection;

import static org.springframework.test.web.client.ExpectedCount.once;

@SpringBootTest
public class KafkaEventHandlerFeatureTest{

    @Autowired
    KafkaEventHandler kafkaEventHandler;

    @MockBean
    RoundScoreJpaRepository roundScoreJpaRepository;

    @Test
    void testHandleEvent() {
        KafkaEvent kafkaEvent = new KafkaEvent();
        kafkaEvent.setType("movement");
        kafkaEvent.setPayload("{}");
        CommandContext commandContext = new CommandContext();

        Round round = new Round();
        round.setGameId("1");
        round.setRoundId("2");

        Player player = new Player();
        player.setId("3");

        commandContext.setRound(round);
        commandContext.setPlayer(player);

        RoundScoreDto roundScoreDto = new RoundScoreDto();
        roundScoreDto.setGame("1");
        roundScoreDto.setRound("2");
        roundScoreDto.setPlayer("3");
        roundScoreDto.setMovementScore(1);

        Mockito.when(
            roundScoreJpaRepository.findByGameAndRoundAndPlayer(
                commandContext.getRound().getGameId(),
                commandContext.getRound().getRoundId(),
                commandContext.getPlayer().getId()
            )
        ).thenReturn(roundScoreDto);
        // Mockito.when(roundScoreJpaRepository.findByGameAndRoundAndPlayer(commandContext.getGameId(), commandContext.getRoundId(), commandContext.getPlayerId())).thenReturn(roundScoreDto.clone());

        kafkaEventHandler.handleEvent(kafkaEvent, commandContext);

        assert roundScoreDto.getMovementScore() == 2;

        // roundScoreDto.setMovementScore(2);
        // Collection<Invocation> invocationMockitoCollection = Mockito.mockingDetails(roundScoreJpaRepository).getInvocations();
        //assert invocationMockitoCollection.stream().toList().get(2).getArgument(0).equals(roundScoreDto);
    }

}

package com.github.tmd.gamelog.adapter.event.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tmd.gamelog.adapter.jpa.MovementScoreDto;
import com.github.tmd.gamelog.adapter.jpa.RoundScoreDto;
import com.github.tmd.gamelog.adapter.jpa.RoundScoreJpaRepository;
import com.github.tmd.gamelog.domain.CommandContext;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.Round;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


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
        roundScoreDto.setGameId("1");
        roundScoreDto.setRoundId("2");
        roundScoreDto.setPlayerId("3");
        MovementScoreDto movementScoreDto = new MovementScoreDto();
        movementScoreDto.setValue(1);
        roundScoreDto.setMovementScore(movementScoreDto);

        Mockito.when(
            roundScoreJpaRepository.findByGameIdAndRoundIdAndPlayerId(
                commandContext.getRound().getGameId(),
                commandContext.getRound().getRoundId(),
                commandContext.getPlayer().getId()
            )
        ).thenReturn(roundScoreDto);
        // Mockito.when(roundScoreJpaRepository.findByGameAndRoundAndPlayer(commandContext.getGameId(), commandContext.getRoundId(), commandContext.getPlayerId())).thenReturn(roundScoreDto.clone());

        kafkaEventHandler.handleEvent(kafkaEvent, commandContext);

        assertThat(roundScoreDto.getMovementScore().getValue()).isEqualTo(1);

        // roundScoreDto.setMovementScore(2);
        // Collection<Invocation> invocationMockitoCollection = Mockito.mockingDetails(roundScoreJpaRepository).getInvocations();
        //assert invocationMockitoCollection.stream().toList().get(2).getArgument(0).equals(roundScoreDto);
    }

}

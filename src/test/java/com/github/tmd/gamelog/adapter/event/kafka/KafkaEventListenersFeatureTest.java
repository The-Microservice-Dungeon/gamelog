package com.github.tmd.gamelog.adapter.event.kafka;

import com.github.tmd.gamelog.adapter.jpa.RoundScoreDto;
import com.github.tmd.gamelog.adapter.jpa.RoundScoreJpaRepository;
import com.github.tmd.gamelog.adapter.rest.client.GameServiceRestClient;
import com.github.tmd.gamelog.domain.CommandContext;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.Round;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.nio.charset.StandardCharsets;

@SpringBootTest
public class KafkaEventListenersFeatureTest {

    @Autowired
    KafkaEventListeners kafkaEventListeners;

    @Autowired
    RoundScoreJpaRepository roundScoreJpaRepository;

    @MockBean
    private GameServiceRestClient gameServiceRestClient;

    @Test
    void testMovementEvent() {
        String transactionId = "123";
        String gameId = "1";
        String playerId = "2";
        String roundId = "3";
        CommandContext commandContext = new CommandContext();
        commandContext.setRound(new Round(gameId, 0, roundId));
        commandContext.setPlayer(new Player(playerId));

        Mockito.when(gameServiceRestClient.fetchCommandContextForTransactionId(transactionId)).thenReturn(commandContext);

        KafkaEvent event = new KafkaEvent();
        event.setType("movement");

        ConsumerRecord<?, ?> kafkaEvent = new ConsumerRecord<>("", 1, 1L, "", "{}");
        Header transactionIdHeader = new RecordHeader("transactionId", "123".getBytes(StandardCharsets.UTF_8));
        kafkaEvent.headers().add(transactionIdHeader);

        kafkaEventListeners.listenMovementTopic(kafkaEvent);

        RoundScoreDto roundScoreDto = this.roundScoreJpaRepository.findByGameAndRoundAndPlayer(
            gameId,
            roundId,
            playerId
        );

        assert roundScoreDto != null;
        assert roundScoreDto.getMovementScore() == 1;
    }
}

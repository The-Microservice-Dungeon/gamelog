package com.github.tmd.gamelog.adapter.event.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tmd.gamelog.adapter.rest_client.CommandContextRepository;
import com.github.tmd.gamelog.domain.CommandContext;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class KafkaEventListeners {

    private final KafkaEventHandler kafkaEventHandler;
    private final CommandContextRepository commandContextRepository;

    public KafkaEventListeners(KafkaEventHandler kafkaEventHandler, CommandContextRepository commandContextRepository)
    {
        this.kafkaEventHandler = kafkaEventHandler;
        this.commandContextRepository = commandContextRepository;
    }

    @KafkaListener(
        id = "1",
        topics = "event",
        groupId = "reflectoring-user",
        containerFactory = "kafkaJsonListenerContainerFactory"
    )
    public void listenMovementTopic(ConsumerRecord<?, ?> kafkaEvent) {
        CommandContext commandContext = getCommandContext(kafkaEvent);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            KafkaEvent event = objectMapper.readValue((String)kafkaEvent.value(), KafkaEvent.class);
            kafkaEventHandler.handleEvent(event, commandContext);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public CommandContext getCommandContext(ConsumerRecord<?, ?> kafkaEvent) {
        Iterable<Header> transactionHeaders = kafkaEvent.headers().headers("transactionId");
        String transactionId = new String(transactionHeaders.iterator().next().value(), StandardCharsets.UTF_8);

        return this.commandContextRepository.findByTransactionId(transactionId);
    }
}

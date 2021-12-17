package com.github.tmd.gamelog.adapter.event.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tmd.gamelog.adapter.rest_client.CommandContextRepository;
import com.github.tmd.gamelog.domain.CommandContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

@Component
@Slf4j
public class KafkaEventListeners {

    private final KafkaEventHandler kafkaEventHandler;
    private final CommandContextRepository commandContextRepository;

    public KafkaEventListeners(KafkaEventHandler kafkaEventHandler, CommandContextRepository commandContextRepository)
    {
        this.kafkaEventHandler = kafkaEventHandler;
        this.commandContextRepository = commandContextRepository;
    }

    @KafkaListener(
        topics = "event"
    )
    public void listenMovementTopic(ConsumerRecord<?, KafkaEvent> kafkaEvent) {
        CommandContext commandContext = getCommandContext(kafkaEvent);
        ObjectMapper objectMapper = new ObjectMapper();
        KafkaEvent event = kafkaEvent.value();
        kafkaEventHandler.handleEvent(event, commandContext);
    }

    public CommandContext getCommandContext(ConsumerRecord<?, ?> kafkaEvent) {
        String transactionId;

        try {
            Iterable<Header> transactionHeaders = kafkaEvent.headers().headers("transactionId");
            transactionId = new String(transactionHeaders.iterator().next().value(), StandardCharsets.UTF_8);
        } catch (NoSuchElementException exception) {
            transactionId = "456";
            // throw new MissingTransactionIdHeader();
        }

        return this.commandContextRepository.findByTransactionId(transactionId);
    }
}

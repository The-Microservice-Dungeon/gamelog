package com.github.tmd.gamelog.adapter.event.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.rest_client.CommandContextRepositoryImpl;
import com.github.tmd.gamelog.domain.CommandContext;
import com.github.tmd.gamelog.domain.CommandContextRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

/**
 * Event Listeners are located here
 */
@Component
@Slf4j
public class KafkaEventListeners {

    private final KafkaEventHandler kafkaEventHandler;
    private final CommandContextRepository commandContextRepository;

    @Autowired
    public KafkaEventListeners(KafkaEventHandler kafkaEventHandler, CommandContextRepository commandContextRepository) {
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

    // TODO
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

    // An example for a Kafka Consumer that leverages Spring Boot, Don't use the ConsumerRecord
    // unless you really need to. Due to the fact that Java performs Type Erasure within generics
    // your actual type might differ within the runtime from compile time.
    // This means, if I would use ConsumerRecord<String, GameStatusEvent> at runtime there would be
    // ConsumerRecord<Object, Object>, allowing other types to sneak into the consumer.
    // Another downside is, that there is no way of Deserializing properly.
    //
    // To put it in a nutshell: Leverage Spring Boot as much as you can. Use the Message abstraction
    // and don't follow tutorials blindly.
    /*@KafkaListener(topics = "status")
    public void consumeGameStatusChangeEvent(@Payload GameStatusEvent event,
        MessageHeaders headers,
        @org.springframework.messaging.handler.annotation.Header(name = "type") String type2) {
        // Imperative way of retrieving the header
        // Declarative way would be using an annotated Method argument
        //          @Header(name = "type") String type
        // (as above)
        var type = headers.get("type", String.class);

        // Shouldn't happen, since we specified it, but we can't be sure and want to get notified
        // whether any of our consumed events does NOT fulfill our event type specification
        if (type == null || type.isBlank()) {
            log.warn("The GameStatusChangeEvent does not have a type");
        }

        // Don't handle the event at the moment, just log it to show how it works
        log.debug("Received GameStatusChangedEvent with payload {} of type {}", event, type);
    }*/
}

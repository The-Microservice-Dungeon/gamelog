package com.github.tmd.gamelog;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingConsumerAwareMessageListener;
import org.springframework.kafka.support.Acknowledgment;

@KafkaListener
public class MessageListenerContainer implements AcknowledgingConsumerAwareMessageListener {

    Logger logger = LoggerFactory.getLogger(MessageListenerContainer.class);

    @Override
    public void onMessage(ConsumerRecord consumerRecord, Acknowledgment acknowledgment, Consumer consumer) {
        logger.info("An INFO Message");
        acknowledgment.acknowledge();
    }

    @Override
    public void onMessage(Object o) {
        logger.info("An INFO Message");
    }
}

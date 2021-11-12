package com.github.tmd.gamelog.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

public class KafkaTopicConfig {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("reflectoring-1").build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("reflectoring-2").build();
    }

}

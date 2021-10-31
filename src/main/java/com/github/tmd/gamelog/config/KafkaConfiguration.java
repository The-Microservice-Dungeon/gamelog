package com.github.tmd.gamelog.config;

import javax.persistence.EntityManagerFactory;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.orm.jpa.JpaTransactionManager;

@Configuration
public class KafkaConfiguration {

  // Provide a custom transaction Manager
  @Bean
  public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

  // Override spring boot config to add the transaction manager defined above
  // Using the JpaTransactionManager Kafka operations are bound to the database transaction
  // context before committing messages, and we achieve therefore an exactly-once semantic
  // on our Listeners
  @Bean
  public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
      ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
      ConsumerFactory<Object, Object> kafkaConsumerFactory,
      JpaTransactionManager jpaTransactionManager) {
    ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
    configurer.configure(factory, kafkaConsumerFactory);

    // Set Transaction Manager
    factory.getContainerProperties().setTransactionManager(jpaTransactionManager);
    return factory;
  }

}

package com.github.tmd.gamelog.adapter.event.kafka;

import java.util.Map;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.converter.ByteArrayJsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.serializer.DelegatingByTypeSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
public class KafkaConfiguration {
  private final KafkaProperties kafkaProperties;

  public KafkaConfiguration(
      KafkaProperties kafkaProperties) {
    this.kafkaProperties = kafkaProperties;
  }

  @Bean
  public ByteArrayJsonMessageConverter byteArrayJsonMessageConverter() {
    return new ByteArrayJsonMessageConverter();
  }

  @Bean
  public ProducerFactory<?, ?> dltHandlerProducerFactory() {
    return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties(), new StringSerializer(),
        new DelegatingByTypeSerializer(Map.of(byte[].class, new ByteArraySerializer(),
            Object.class, new JsonSerializer<Object>())));
  }

  @Bean
  @Primary
  public ProducerFactory<?, ?> kafkaProducerFactory(
      ObjectProvider<DefaultKafkaProducerFactoryCustomizer> customizers) {
    DefaultKafkaProducerFactory<?, ?> factory = new DefaultKafkaProducerFactory<>(
        this.kafkaProperties.buildProducerProperties());
    String transactionIdPrefix = this.kafkaProperties.getProducer().getTransactionIdPrefix();
    if (transactionIdPrefix != null) {
      factory.setTransactionIdPrefix(transactionIdPrefix);
    }
    customizers.orderedStream().forEach((customizer) -> customizer.customize(factory));
    return factory;
  }

  @Bean
  public KafkaTemplate<?, ?> retryTopicDefaultKafkaTemplate(ProducerListener<Object, Object> kafkaProducerListener) {
    KafkaTemplate<Object, Object> kafkaTemplate = new KafkaTemplate(dltHandlerProducerFactory());
    kafkaTemplate.setMessageConverter(byteArrayJsonMessageConverter());
    kafkaTemplate.setProducerListener(kafkaProducerListener);
    kafkaTemplate.setDefaultTopic(this.kafkaProperties.getTemplate().getDefaultTopic());
    return kafkaTemplate;
    // return new KafkaTemplate<>(dltHandlerProducerFactory());
  }

  @Bean
  @Primary
  public KafkaTemplate<?, ?> kafkaTemplate(ProducerFactory<Object, Object> kafkaProducerFactory,
      ProducerListener<Object, Object> kafkaProducerListener,
      ObjectProvider<RecordMessageConverter> messageConverter) {
    KafkaTemplate<Object, Object> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
    messageConverter.ifUnique(kafkaTemplate::setMessageConverter);
    kafkaTemplate.setProducerListener(kafkaProducerListener);
    kafkaTemplate.setDefaultTopic(this.kafkaProperties.getTemplate().getDefaultTopic());
    return kafkaTemplate;
  }


  @Bean
  public CommonLoggingErrorHandler errorHandler() {
    return new CommonLoggingErrorHandler();
  }
}

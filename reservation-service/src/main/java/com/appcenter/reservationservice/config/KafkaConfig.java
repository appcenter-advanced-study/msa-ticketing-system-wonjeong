package com.appcenter.reservationservice.config;

import com.appcenter.reservationservice.kafka.event.stock.StockFailedEvent;
import com.appcenter.reservationservice.kafka.event.ticket.TicketFailedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    private final String bootstrapServers = "localhost:9092";
    private final String trustedPackages = "com.appcenter.reservationservice.kafka";

    /** 공통 Producer 설정 **/
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);  // type header 비활성화
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /** 공통 Consumer 기본 설정 **/
    private Map<String, Object> baseConsumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }

    /** TicketFailedEvent ConsumerFactory **/
    @Bean
    public ConsumerFactory<String, TicketFailedEvent> ticketFailedConsumerFactory() {
        JsonDeserializer<TicketFailedEvent> deserializer = new JsonDeserializer<>(TicketFailedEvent.class);
        deserializer.addTrustedPackages(trustedPackages);
        deserializer.setUseTypeHeaders(false);
        return new DefaultKafkaConsumerFactory<>(baseConsumerConfigs(), new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TicketFailedEvent> ticketFailedKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TicketFailedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(ticketFailedConsumerFactory());
        return factory;
    }

    /** StockFailedEvent ConsumerFactory **/
    @Bean
    public ConsumerFactory<String, StockFailedEvent> stockFailedConsumerFactory() {
        JsonDeserializer<StockFailedEvent> deserializer = new JsonDeserializer<>(StockFailedEvent.class);
        deserializer.addTrustedPackages(trustedPackages);
        deserializer.setUseTypeHeaders(false);
        return new DefaultKafkaConsumerFactory<>(baseConsumerConfigs(), new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, StockFailedEvent> stockFailedKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, StockFailedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stockFailedConsumerFactory());
        return factory;
    }
}
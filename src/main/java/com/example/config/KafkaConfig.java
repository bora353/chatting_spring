//package com.example.config;
//
//import com.example.model.ChattingMessage;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import io.lettuce.core.ClientOptions;
//import io.lettuce.core.SocketOptions;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.*;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableKafka
//public class KafkaConfig {
//
//    // Receiver
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, ChattingMessage> kafkaListenerContainerFactory(){
//        ConcurrentKafkaListenerContainerFactory<String, ChattingMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//    @Bean
//    public ConsumerFactory<String, ChattingMessage> consumerFactory(){
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), new JsonDeserializer<>(ChattingMessage.class));
//    }
//
//    @Bean
//    public Map<String, Object> consumerConfigs(){
//        // 레디스 사용하는 것으로 변경 필요!!
////        return ImmutableMap.<String, Object>builder()
////                .put("bootstrap.servers", "localhost:9092")
////                .put("key.deserializer", IntegerDeserializer.class)
////                .put("value.deserializer", JsonDeserializer.class)
////                .put("group.id", "spring-boot-test")
////                .build();
//        //------------------------
//        /*Map<String, Object> config = new HashMap<>();
//        // Kafka Bootstrap Servers 설정
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        // 기타 Kafka Consumer 설정
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
//        return config;*/
//
//        Map<String, Object> configurations = new HashMap<>();
//        configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        configurations.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
//        configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,  JsonDeserializer.class);
//        configurations.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
//        return configurations;
//    }
//
//
//    // Sender
//    @Bean
//    public ProducerFactory<String, ChattingMessage> producerFactory(){
//        return new DefaultKafkaProducerFactory<>(producerConfigs(), null, new JsonSerializer<ChattingMessage>());
//    }
//
//    @Bean
//    public KafkaTemplate<String, ChattingMessage> kafkaTemplate(){
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//    @Bean
//    public Map<String, Object> producerConfigs(){
//
//        // 레디스 사용하는 것으로 변경 필요!!
////        return ImmutableMap.<String, Object>builder()
////                .put("bootstrap.servers", "localhost:9092")//kafka server ip & port
////                .put("key.serializer", IntegerSerializer.class)
////                .put("value.serializer", JsonSerializer.class)//Object json parser
////                .put("group.id", "spring-boot-test") // chatting  group id
////                .build();
//        Map<String, Object> config = new HashMap<>();
//        // Kafka Bootstrap Servers 설정
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        // 기타 Kafka Producer 설정
//        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
//        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
//        return config;
//    }
//
//    // Lettuce Redis 연동 설정
//    @Bean
//    public LettuceConnectionFactory lettuceConnectionFactory() {
//        ClientOptions clientOptions = ClientOptions.builder()
//                .socketOptions(SocketOptions.builder().connectTimeout(Duration.ofSeconds(10)).build())
//                .build();
//
//        return new LettuceConnectionFactory(clientOptions);
//    }
//
//}

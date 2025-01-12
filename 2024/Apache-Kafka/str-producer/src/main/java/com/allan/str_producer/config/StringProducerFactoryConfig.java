package com.allan.str_producer.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class StringProducerFactoryConfig {

    @Autowired // KafkaProperties pega o valor das properties definido no arquivo application.yml
    private KafkaProperties properties;

    @Bean
    public ProducerFactory<String, String> producerFactory(){
        // Configurar Serializador de chave e valor das mensagens enviadas pelo produtor como um StringSerializer que é
        // do próprio Kafka, já que Kafka trabalha com envio de mensagens utilizando chave-valor
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory){
        // Criando template de mensagem através do ProducerFactory criado anteriormente
        return new KafkaTemplate<>(producerFactory);
    }
}

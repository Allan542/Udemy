package com.allan.str_producer.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class StringProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // Criando service para envio de mensagens a uma das partições do tópíco str-topic
    public void sendMessage(String message) {
        kafkaTemplate.send("str-topic", message).whenComplete((result, e) -> {
           if(e == null && result != null){
               log.info("Send message with success {}", message);
               log.info("Partition {}, Offset {}", result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
           } else {
               log.error("Error send message");
           }
        });
    }
}

package com.allan.str_consumer.listeners;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

//@Slf4j
@Component
public class StrConsumerListener {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(groupId = "group-1", topics = "str-topic", containerFactory = "strContainerFactory")
    public void listener(String message){
        log.info("Receive message {}", message);
    }
}
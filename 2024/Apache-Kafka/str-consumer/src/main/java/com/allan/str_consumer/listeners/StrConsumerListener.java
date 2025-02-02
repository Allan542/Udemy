package com.allan.str_consumer.listeners;

import com.allan.str_consumer.custom.StrConsumerCustomListener;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

//@Log4j2
@Component
public class StrConsumerListener {

    //Existe a seguinte situação: você tem um tópico com 2 partições que estão sendo ouvidas por um grupo de 1 único listener integrante.
    // Quando se coloca mais um listener no mesmo grupo, o kafka dividirá as duas partições entre os dois listeners, para cada um escutar
    // 1 partição cada neste caso. Se houver mais partições, se dividirá entre os listeners. Se for criado um terceiro listener para as
    // duas partições que está no mesmo grupo, o terceiro listener não se associará a nenhuma partição, pois as duas partições existentes
    // já estão ocupadas pelos outros listeners do mesmo grupo. Se for criado um segundo grupo de listeners, o listener associado ao segundo
    // grupo escutará ambas as partições criadas por ser único nesse novo grupo, pois neste caso é um novo grupo de listeners que está escutando
    // as mensagens do tópico.
    Logger log = LoggerFactory.getLogger(this.getClass());

//    @KafkaListener(groupId = "group-1",
//        topicPartitions = {
//            @TopicPartition(topic = "str-topic", partitions = {"0"}) // Define qual tópico e qual(is) partição(ões) do mesmo, este listener olhará
//        }
//        , containerFactory = "strContainerFactory")
    @SneakyThrows
    @StrConsumerCustomListener(groupId = "group-1")
    public void create(String message){
        log.info("CREATE ::: Receive message {}", message);
        throw new IllegalArgumentException("EXCEPTION ...");
    }

//    @KafkaListener(groupId = "group-1",
//        topicPartitions = {
//        @TopicPartition(topic = "str-topic", partitions = {"1"})
//    }
//    , containerFactory = "strContainerFactory")
    @StrConsumerCustomListener(groupId = "group-1")
    public void log(String message){
        log.info("LOG ::: Receive message {}", message);
    }

    @StrConsumerCustomListener(groupId = "group-2", containerFactory = "validMessageContainerFactory")
//    @KafkaListener(groupId = "group-2", topics = "str-topic", containerFactory = "validMessageContainerFactory")
    public void history(String message){
        log.info("HISTORY ::: Receive message {}", message);
    }
}
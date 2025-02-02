package com.allan.str_consumer.custom;

import org.springframework.core.annotation.AliasFor;
import org.springframework.kafka.annotation.KafkaListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Informando ao compilador Java e JVM que essa anotação deve estar disponível por meio de reflexões (Reflections) em tempo de execução
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) // Anotação que deve ser definida em cima de um método e nada mais
@KafkaListener
public @interface StrConsumerCustomListener {

    // Pode ser usado para decorar atributos dentro de uma única anotação, ou basicamente permitir que um único valor
    // seja informado a múltiplos métodos neste caso
    @AliasFor(annotation = KafkaListener.class, attribute = "topics")
    String[] topics() default "str-topic";

    @AliasFor(annotation = KafkaListener.class, attribute = "containerFactory")
    String containerFactory() default "strContainerFactory";

    @AliasFor(annotation = KafkaListener.class, attribute = "groupId")
    String groupId() default "";

    @AliasFor(annotation = KafkaListener.class, attribute = "errorHandler")
    String errorHandler() default "errorCustomHandler";
}

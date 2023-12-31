package com.udemy.parimparjob.config.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImprimeWriterConfig {

    @Bean
    public ItemWriter<String> imprimeWriter(){
        return items -> items.forEach(System.out::println);
    }
}

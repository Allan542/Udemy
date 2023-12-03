package com.udemy.DemonstrativoOrcamentarioJob.writer;

import com.udemy.DemonstrativoOrcamentarioJob.dominio.DemonstrativoOrcamentario;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemonstrativoOrcamentarioWriterConfig {

    @Bean
    public ItemWriter<DemonstrativoOrcamentario> demonstrativoOrcamentarioItemWriter(){
        return items -> items.forEach(System.out::println);
    }
}

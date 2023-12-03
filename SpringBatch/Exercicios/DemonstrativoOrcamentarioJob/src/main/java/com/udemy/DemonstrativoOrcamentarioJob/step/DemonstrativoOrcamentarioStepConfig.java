package com.udemy.DemonstrativoOrcamentarioJob.step;

import com.udemy.DemonstrativoOrcamentarioJob.dominio.DemonstrativoOrcamentario;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemonstrativoOrcamentarioStepConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step step(MultiResourceItemReader<DemonstrativoOrcamentario> reader,
                     ItemWriter<DemonstrativoOrcamentario> writer){
        return stepBuilderFactory
            .get("demonstrativoOrcamentarioStep")
            .<DemonstrativoOrcamentario, DemonstrativoOrcamentario>chunk(1)
            .reader(reader)
            .writer(writer)
            .build();
    }
}

package com.udemy.parimparjob.config.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ImprimeParImparStepConfig {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Step imprimeParImparStep(IteratorItemReader<Integer> reader,
                                    FunctionItemProcessor<Integer, String> processor,
                                    ItemWriter<String> writer) throws Exception {
        return stepBuilderFactory
            .get("imprimeParImparStep")
            .<Integer, String>chunk(1)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }






}

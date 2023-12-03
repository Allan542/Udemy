package com.udemy.contasbancariasjob.step;

import com.udemy.contasbancariasjob.dominio.Cliente;
import com.udemy.contasbancariasjob.dominio.Conta;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContasBancariasStepConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step contasBancariasStep(
        ItemReader<Cliente> reader,
        ItemProcessor<Cliente, Conta> processor,
        ItemWriter<Conta> writer
    ){
        return stepBuilderFactory
            .get("contasBancariasStep")
            .<Cliente, Conta>chunk(10)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }
}

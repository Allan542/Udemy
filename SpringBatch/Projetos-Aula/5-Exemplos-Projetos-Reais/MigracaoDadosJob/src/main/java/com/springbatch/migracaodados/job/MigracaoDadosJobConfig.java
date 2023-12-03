package com.springbatch.migracaodados.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@EnableBatchProcessing
@Configuration
public class MigracaoDadosJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job migracaoDadosJob(
        @Qualifier("migrarPessoaStep") Step migrarPessoaStep,
        @Qualifier("migrarDadosBancariosStep") Step migrarDadosBancariosStep){
        return jobBuilderFactory
            .get("migracaoDadosJob")
            .incrementer(new RunIdIncrementer())
            .start(stepsParalelos(migrarPessoaStep, migrarDadosBancariosStep))
            .end() // finaliza o fluxo
            .build();
    }

    // Perigo: não abusar deste recurso, porque o gerenciamento de threads consomem recursos e a execução paralela depende de hardware, ou seja, quantos processadores físicos a sua máquina vai ter para arcar com essa execução simultânea
    private Flow stepsParalelos(Step migrarPessoaStep, Step migrarDadosBancariosStep) {
        Flow migrarDadosBancariosFlow = new FlowBuilder<Flow>("migrarDadosBancariosFlow")
            .start(migrarDadosBancariosStep) // cria um fluxo com migrarDadosBancariosStep e inicia o step. É uma abstração acima do step
            .build();

        Flow stepsParalelos = new FlowBuilder<Flow>("stepsParalelosFlow")
            .start(migrarPessoaStep)
            .split(new SimpleAsyncTaskExecutor()) // Divide a tarefa através de uma thread para que a outra thread possa executar essa step e esta thread possa executar a outra/próxima step. Este TaskExecutor é uma implementação do SpringBatch para execuções assincronas
            .add(migrarDadosBancariosFlow) // Dispara a inicialização da execução deste flow já que vão executar ao mesmo tempo
            .build();

        return stepsParalelos;
    }
}

package com.springbatch.bdpartitionerlocal.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableBatchProcessing
public class CargaMassivaJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job cargaMassivaJob(//@Qualifier("pessoaStep") Step pessoaStep,
                               @Qualifier("pagamentosStep") Step pagamentosStep){
        return jobBuilderFactory.get("cargaMassivaJob")
            .incrementer(new RunIdIncrementer())
//            .start(pessoaStep)
            .start(pagamentosStep)
            .build();
    }
}

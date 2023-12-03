package br.com.primeiroprojetospringbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchJobConfig {
  @Autowired
  private JobBuilderFactory jobBuilderFactory;


  @Bean
  public Job imprimeOlaJob(Step imprimeOlaStep) {
    return jobBuilderFactory.get("imprimeOlaJob").start(imprimeOlaStep).build();
  }
}

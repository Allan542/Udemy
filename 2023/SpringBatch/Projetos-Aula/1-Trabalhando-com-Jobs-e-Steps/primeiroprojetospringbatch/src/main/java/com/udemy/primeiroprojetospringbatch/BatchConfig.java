package com.udemy.primeiroprojetospringbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing // Nesta versão (Spring 3), ainda é necessária ter esta anotação
@Configuration
public class BatchConfig {
    @Autowired // Factory para construir job
    private JobBuilderFactory jobBuilderFactory;
    @Autowired // Factory para construir steps
    private StepBuilderFactory stepBuilderFactory;

    @Bean // Só o job precisa do @Bean, já que os steps são "localizados" por este método, então não são necessários ter uma anotação @Bean
    public Job imprimeOlaJob() {
        return jobBuilderFactory
            .get("imprimeOlaJob") // Nome do job
            .start(imprimeOlaStep()) // Dá o start no step
            .incrementer(new RunIdIncrementer()) // Este auto-incremento impede a reinicialização do job. Quando ele é usado, cria uma nova instância do job
            .build();
    }

    public Step imprimeOlaStep(){
        return stepBuilderFactory
            .get("imprimeOlaStep")// Nome do step
            .tasklet(imprimeOlaTasklet(null))
            .build();
    }

    @Bean // Spring precisa entender este método
    @StepScope // Para que ele pegue o valor dentro do argumento, é preciso que este método seja uma StepScope ou um escopo de step
    public Tasklet imprimeOlaTasklet(@Value("#{jobParameters['nome']}") String nome) { // Forma de pegar o valor de um CLI argument. Este cara é case-sensitive, ou seja, nome != NOME
        return new Tasklet() { // Criação das regras de uma step simples chamada tasklet, que é uma interface e precisa ser instanciada para implementar o método execute ou usar lambda
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println(String.format("Olá, %s", nome));
                return RepeatStatus.FINISHED; // Retorna um RepeatSatus do Batch que pode ser Continuable ou Finished
            }
        };
    }
}

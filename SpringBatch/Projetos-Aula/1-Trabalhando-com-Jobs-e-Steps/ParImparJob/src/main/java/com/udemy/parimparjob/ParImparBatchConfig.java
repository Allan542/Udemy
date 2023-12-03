package com.udemy.parimparjob;

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
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.batch.api.chunk.ItemProcessor;
import javax.batch.api.chunk.ItemReader;
import java.util.Arrays;
import java.util.List;

@EnableBatchProcessing // Nesta versão (Spring 3), ainda é necessária ter esta anotação
@Configuration
public class ParImparBatchConfig {
    @Autowired // Factory para construir job
    private JobBuilderFactory jobBuilderFactory;
    @Autowired // Factory para construir steps
    private StepBuilderFactory stepBuilderFactory;

    @Bean // Só o job precisa do @Bean, já que os steps são "localizados" por este método, então não são necessários ter uma anotação @Bean
    public Job imprimeParImparJob() {
        return jobBuilderFactory
            .get("imprimeParImparJob") // Nome do job
            .start(imprimeParImparStep()) // Dá o start no step
//            .incrementer(new RunIdIncrementer()) // Este auto-incremento impede a reinicialização do job. Quando ele é usado, cria uma nova instância do job
            .build();
    }

    public Step imprimeParImparStep(){
        return stepBuilderFactory
            .get("imprimeParImparStep")
            // O chunk define
            .<Integer, String>chunk(1) // Quando declara um chunk, o leitor e o escritor são obrigatórios, porém processadores são opcionais
            // Para cada transação, um commit será feito de acordo com o número de chunks. Ou seja, se tiver 2 chunks e 10 dados, será feito 5 commits de transações e um commit final. Quando é um chunkSize cada chunk seria uma transação.
            // Se aumentar o tamanho do chunk para 10, seria 10 transações para um chunk e um commit que podem ser perdidos no meio do caminho por qualquer interrupção
            // O custo para criar uma transação é alto. O banco cria uma estrutura de sandbox para ele trabalhar com as transações, rodar consulta sem commitar os resultados, segurar em memória para depois commitar
            // Aumentar o número de transações para serem seguradas, pode ter um alto risco de afetar performance com problema de memória, por segurar na mesma ou até um encerramento repentino.
            // O ideal é aumentar ao máximo possível, mas também pensar em memória. Se aumentar muito e a operação também demorar muito, pode haver uma falha no meio do processamento e simplesmente não salvar nada.
            // Por isso que é importante salvar pedaços. Por isso que é bom estudar algumas informações, como a massa de dados, a estrutura do servidor, como processamento, memória etc.
            // O valor ideal do chunkSize só pode ser descoberto por teste empírico, ou seja, ir testando o valor até chegar no adequado
            .reader(contaAteDezReader())
            .processor(parOuImparProcessor())
            .writer(imprimeWriter())
            .build();
    }

    public IteratorItemReader<Integer> contaAteDezReader(){
        List<Integer> numerosDeUmAteDez = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return new IteratorItemReader<Integer>(numerosDeUmAteDez.iterator());
    }

    public FunctionItemProcessor<Integer, String> parOuImparProcessor() {
        return new FunctionItemProcessor<>
            (item -> item % 2 == 0 ? String.format("Item %s é Par", item) : String.format("Item %s é Ímpar", item));
    }

    public ItemWriter<String> imprimeWriter(){
        return items -> items.forEach(i -> {
//            if(i.equals("Item 8 é Par")) throw new RuntimeException("Teste");
            System.out.println(i);
        });
    }
}

package com.springbatch.bdremotepartitioner.job;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.integration.partition.RemotePartitioningManagerStepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.messaging.MessageChannel;

@Profile("manager") // Quando executarmos o manager, a gente passa um parâmetro de execução dizendo que o profile que a gente vai executar é o Manager. Só vai injetar os componentes que tem apenas o manager, mas vamos injetar os componentes que o manager precisa que é o banco de dados
@Configuration
@EnableBatchProcessing
@EnableBatchIntegration // Habilitar o processamento integrado
// Tanto o manager quanto o worker são configurados como jobs diferentes, porque eles vão executar independentemente
// Responsável por salvar os metadados do job
public class ManagerConfig {
    private static final int GRID_SIZE=2;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired // Esse componente que permite a execução remota
    private RemotePartitioningManagerStepBuilderFactory stepBuilderFactory;

    @Bean // Como é um manager, ele define o job. Quem define a implementação dos steps são os workers
    public Job remotePartitioningJob(
        @Qualifier("migrarPessoaStep") Step migrarPessoaStep,
        @Qualifier("migrarDadosBancariosStep") Step migrarDadosBancariosStep
        ) {
        return jobBuilderFactory
            .get("remotePartitioningJob")
            .start(migrarPessoaStep)
            .next(migrarDadosBancariosStep)
            .incrementer(new RunIdIncrementer())
            .build();
    }

    @Bean // Esse é o stepManager igual do particionamento local, mesmo que o componente para build de step seja diferente
    public Step migrarPessoaStep(
        @Qualifier("pessoaPartitioner") Partitioner partitioner
        ) {
        return stepBuilderFactory
            .get("migrarPessoaStep")
            .partitioner("migrarPessoaStep", partitioner)
            .gridSize(GRID_SIZE)
            .outputChannel(requests())// Quando solicitar a execução dessa step, alguma mensagem deve sair para ser mandada ao message broker. Ele vai enviar uma mensagem na fila
            .build();
    }

    @Bean // Esse é o stepManager igual do particionamento local, mesmo que o componente para build de step seja diferente
    public Step migrarDadosBancariosStep(
        @Qualifier("dadosBancariosPartitioner") Partitioner partitioner
    ) {
        return stepBuilderFactory
            .get("migrarDadosBancariosStep")
            .partitioner("migrarDadosBancariosStep", partitioner)
            .gridSize(GRID_SIZE)
            .outputChannel(requests()) // Quando solicitar a execução dessa step, alguma mensagem deve sair para ser mandada ao message broker
            .build();
    }

    @Bean // Responsável por mandar mensagens de processamento para a fila JMS que está sendo armazenada no ActiveMQ
    public IntegrationFlow outBoundFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows
            .from(requests())
            .handle(Jms.outboundAdapter(connectionFactory)
                .destination("requests")) // Tem que falar para a mesma fila que o worker vai consumir do ActiveMQ. Assim ele funciona com o worker
            .get();
    }

    @Bean
    public DirectChannel requests() {
        return new DirectChannel();
    }
}

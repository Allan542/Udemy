package com.springbatch.bdremotepartitioner.job;

import com.springbatch.bdremotepartitioner.dominio.DadosBancarios;
import com.springbatch.bdremotepartitioner.dominio.Pessoa;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.integration.partition.RemotePartitioningManagerStepBuilderFactory;
import org.springframework.batch.integration.partition.RemotePartitioningWorkerStepBuilderFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
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
import org.springframework.transaction.PlatformTransactionManager;

@Profile("worker") // Quando executarmos o manager, a gente passa um parâmetro de execução dizendo que o profile que a gente vai executar é o Manager. Só vai injetar os componentes que tem apenas o manager, mas vamos injetar os componentes que o manager precisa que é o banco de dados
@Configuration
@EnableBatchProcessing
@EnableBatchIntegration // Habilitar o processamento integrado
// Tanto o manager quanto o worker são configurados como jobs diferentes, porque eles vão executar independentemente, com a diferença que esse cara vai ficar executando como um processo. Então quando ele rodar, vai ficar ativo esperando requisições do message broker e sempre que chegarem, ele vai realizar o processamento que deve ser realizado,
// da partição que for responsabilidade dele. Aqui não define os jobs, apenas os steps. Quem salva os metadados da step do SpringBatch é esse cara e do job é o Manager
public class WorkerConfig {
    @Autowired // Executar o worker de forma remota
    private RemotePartitioningWorkerStepBuilderFactory stepBuilderFactory;

    @Autowired // Garantir que seja aplicada a transação do banco secundário do banco de dados
    @Qualifier("transactionManagerApp")
    private PlatformTransactionManager transactionManagerApp;

    @Bean
    public Step migrarPessoaStep(
        JdbcPagingItemReader<Pessoa> pessoaReader,
        JdbcBatchItemWriter<Pessoa> pessoaWriter
    ) {
        return stepBuilderFactory
            .get("migrarPessoaStep")
            .inputChannel(requests()) // Utilizar um canal de comunicação para consumir os dados para a fila
            .<Pessoa, Pessoa>chunk(10000)
            .reader(pessoaReader)
            .writer(pessoaWriter)
            .transactionManager(transactionManagerApp)
            .build();
    }

    @Bean
    public Step migrarDadosBancariosStep(
        JdbcPagingItemReader<DadosBancarios> dadosBancariosReader,
        JdbcBatchItemWriter<DadosBancarios> dadosBancariosWriter
    ){
        return stepBuilderFactory
            .get("migrarDadosBancariosStep")
            .inputChannel(requests()) // Utilizar um canal de comunicação para consumir os dados para a fila
            .<DadosBancarios, DadosBancarios>chunk(10000)
            .reader(dadosBancariosReader)
            .writer(dadosBancariosWriter)
            .transactionManager(transactionManagerApp)
            .build();
    }

    @Bean // Descreve em detalhe o fluxo de comunicação entre manager e worker, utilizando uma fila JMS chamada requests e estará num Middleware do ActiveMQ que está no nosso brokerConfig
    // É reponsável por receber mensagens da fila JMS que está salva no ActiveMQ e o destino das mensagens é a fila request
    public IntegrationFlow inboundFlow(ActiveMQConnectionFactory connectionFactory){
        return IntegrationFlows.from(Jms.messageDrivenChannelAdapter(connectionFactory) // Pra dizer que a mensagem foi consumida. Basicamente está falando que vamos receber mensagens JMS do ActiveMQ
            .destination("requests")) // Nome da fila que vai mandar uma mensagem de destino para o manager
            .channel(requests()).get(); // Informa o canal e pega ele, para devolver o flow que acabou de criar
    }

    @Bean
    public MessageChannel requests() {
        return new DirectChannel();
    }

}

package com.springbatch.remotechunkingjob.job;

import com.springbatch.remotechunkingjob.dominio.Pessoa;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.integration.chunk.RemoteChunkingManagerStepBuilderFactory;
import org.springframework.batch.integration.chunk.RemoteChunkingWorkerBuilder;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
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

@Profile("worker")
@Configuration
@EnableBatchProcessing
@EnableBatchIntegration
public class WorkerConfig {

    // No chunking remoto, como já configuramos quase tudo relacionado a job e até mesmo uma boa parte do StepWorker, aqui será configurado apenas o necessário, que é o flow de processamento, incluindo quem é processor e quem é writer aqui
    @Autowired
    private RemoteChunkingWorkerBuilder<Pessoa, Pessoa> pessoaWorkerBuilder;

    @Bean
    public IntegrationFlow integrationFlow(
        ItemProcessor<Pessoa, Pessoa> pessoaItemProcessor,
        ItemWriter<Pessoa> pessoaItemWriter
    ) {
        return pessoaWorkerBuilder
            .itemProcessor(pessoaItemProcessor)
            .itemWriter(pessoaItemWriter)
            .inputChannel(requests())
            .outputChannel(replies())
            .build();
    }

    @Bean
    public DirectChannel requests() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel replies() {
        return new DirectChannel(); // Terá vários worker producer de replies para apenas um manager consumer
    }

    @Bean // Fluxo de requisições
    public IntegrationFlow outboundFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows
            .from(replies()) // Neste caso, vai enviar as requisições para o Message broker, que é a fila ActiveMQ
            .handle(Jms.outboundAdapter(connectionFactory)
                .destination("replies")) // Configura a mensagem Jms de saída e o nome da fila dentro do message broker
            .get();
    }

    @Bean
    public IntegrationFlow inboundFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows
            .from(Jms.messageDrivenChannelAdapter(connectionFactory)
                .destination("requests")) // Está dizendo que: neste método de entrada, vai receber as mensagens da fila Jms, que está configurada através deste broker e fazer requisições para consumir da fila de destino que está configurada no ActiveMQ, pelo produtor da fila
            .channel(requests())
            .get();
    }
}

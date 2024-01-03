package com.springbatch.remotechunkingjob.job;

import com.springbatch.remotechunkingjob.dominio.Pessoa;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.chunk.RemoteChunkingManagerStepBuilderFactory;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.transaction.PlatformTransactionManager;

@Profile("manager")
@Configuration
@EnableBatchProcessing
@EnableBatchIntegration
public class ManagerConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private RemoteChunkingManagerStepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("transactionManagerApp")
    private PlatformTransactionManager transactionManagerApp;

    @Bean
    public Job remoteChunkingJob(
        @Qualifier("migrarPessoaWorkerStep") Step migrarPessoaStep,
        @Qualifier("migrarDadosBancariosStep") Step migrarDadosBancariosStep
    ) {
        return jobBuilderFactory
            .get("remoteChunkingJob")
            .start(migrarPessoaStep)
            .next(migrarDadosBancariosStep)
            .incrementer(new RunIdIncrementer())
            .build();
    }

    @Bean
    public Step migrarPessoaWorkerStep(
        ItemReader<Pessoa> arquivoPessoaReader
    ) {
        return stepBuilderFactory
            .get("migrarPessoaWorkerStep")
            .chunk(15) // não tem a necessidade do tipo de entrada e saída por executar remotamente
            .reader(arquivoPessoaReader) // A leitura é feita localmente. Não é igual o partitionamento remoto que tudo é remoto. Não é necessário um processor e um writer
            .outputChannel(requests())
            .inputChannel(replies())
            .transactionManager(transactionManagerApp)
            .build();
    }

    @Bean
    public QueueChannel replies() {
        return new QueueChannel(); // Neste caso, apenas uma máquina pode obter a resposta, que é a máquina manager, que é o que esse QueueChannel faz. (Resumindo, pode ter vários producers da fila, que estão configurados como DiretChannel, mas apenas um consumer, que está configurado como QueueChannel)
    }

    @Bean
    public DirectChannel requests() {
        return new DirectChannel(); // Significa que várias máquinas poderão receber essa requisição que chegar (no caso, qualquer worker podem pegar a mensagem que este manager mandar. Pode ter tanto vários consumers, quanto vários producers, porque tanto a manager como a worker usam DirectChannel)
    }

    @Bean // Fluxo de requisições
    public IntegrationFlow outboundFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows
            .from(requests()) // Neste caso, vai enviar as requisições para o Message broker, que é a fila ActiveMQ
            .handle(Jms.outboundAdapter(connectionFactory)
                .destination("requests")) // Configura a mensagem Jms de saída e o nome da fila dentro do message broker
            .get();
    }

    @Bean
    public IntegrationFlow inboundFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows
            .from(Jms.messageDrivenChannelAdapter(connectionFactory)
                .destination("replies")) // Está dizendo que: neste método de entrada, vai receber as mensagens da fila Jms, que está configurada através deste broker e fazer requisições para consumir da fila de destino que está configurada no ActiveMQ, pelo produtor da fila
            .channel(replies())
            .get();
    }
}

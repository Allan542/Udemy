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

	@Profile("manager")
@Configuration
@EnableBatchProcessing
@EnableBatchIntegration
public class ManagerConfig {
	private static final int GRID_SIZE=2;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private RemotePartitioningManagerStepBuilderFactory stepBuilderFactory;

	@Bean
	public DirectChannel requests() {
		return new DirectChannel();
	}
	
	@Bean
	public Job remotePartitioningJob(
			@Qualifier("migrarPessoaStep") Step migrarPessoaStep,
			@Qualifier("migrarDadosBancariosStep") Step migrarDadosBancariosStep) {
		return jobBuilderFactory
				.get("remotePartitioningJob")
				.start(migrarPessoaStep)
				.next(migrarDadosBancariosStep)
				.incrementer(new RunIdIncrementer())
				.build();
	}
	
	@Bean
	public Step migrarPessoaStep(
			@Qualifier("pessoaPartitioner") Partitioner partitioner) {
		return stepBuilderFactory
				.get("migrarPessoaStep")
				.partitioner("migrarPessoaStep", partitioner)
				.gridSize(GRID_SIZE)
				.outputChannel(requests())
				.build();
	}
	
	@Bean
	public Step migrarDadosBancariosStep(
			@Qualifier("dadosBancariosPartitioner") Partitioner partitioner) {
		return stepBuilderFactory
				.get("migrarDadosBancariosStep")
				.partitioner("migrarDadosBancariosStep", partitioner)
				.gridSize(GRID_SIZE)
				.outputChannel(requests())
				.build();
	}

	@Bean
	public IntegrationFlow outBoundFlow(ActiveMQConnectionFactory connectionFactory) {
		return IntegrationFlows.from(requests())
			.handle(Jms.outboundAdapter(connectionFactory)
				.destination("requests"))
			.get();
	}

}

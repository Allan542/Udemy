package com.springbatch.simplepartitionerlocal.step;

import com.springbatch.simplepartitionerlocal.dominio.Pessoa;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.simplepartitionerlocal.dominio.DadosBancarios;

@Configuration
public class MigrarDadosBancariosStepConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("transactionManagerApp")
	private PlatformTransactionManager transactionManagerApp;

	@Bean
	public Step migrarDadosBancariosManager(
		@Qualifier("dadosBancariosPartitioner") Partitioner pessoaPartitioner,
		@Qualifier("dadosBancariosPartitionReader") ItemStreamReader<DadosBancarios> arquivoPessoaReader,
		JdbcBatchItemWriter<DadosBancarios> pessoaWriter,
		TaskExecutor taskExecutor
	) {
		return stepBuilderFactory
			.get("migrarDadosBancariosManager")
			.partitioner("migrarDadosBancariosStep.manager", pessoaPartitioner)
			.step(migrarDadosBancariosStep(arquivoPessoaReader, pessoaWriter))
			.gridSize(10)
			.taskExecutor(taskExecutor)
			.build();
	}

	private Step migrarDadosBancariosStep(ItemReader<DadosBancarios> arquivoDadosBancariosReader,
			JdbcBatchItemWriter<DadosBancarios> bancoDadosBancariosWriter) {
		return stepBuilderFactory
				.get("migrarDadosBancariosStep")
				.<DadosBancarios, DadosBancarios>chunk(2000)
				.reader(arquivoDadosBancariosReader)
				.writer(bancoDadosBancariosWriter)
				.transactionManager(transactionManagerApp)
				.build();
	}
}

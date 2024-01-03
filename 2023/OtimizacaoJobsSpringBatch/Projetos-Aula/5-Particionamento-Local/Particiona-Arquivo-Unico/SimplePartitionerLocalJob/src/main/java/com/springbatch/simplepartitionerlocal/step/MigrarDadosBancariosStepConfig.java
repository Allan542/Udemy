package com.springbatch.simplepartitionerlocal.step;

import com.springbatch.simplepartitionerlocal.dominio.Pessoa;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.simplepartitionerlocal.dominio.DadosBancarios;

@Configuration
public class MigrarDadosBancariosStepConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	// O ideal é que em vez de colocar essa informação hard coded, a gente coloca na properties
	@Value("${migracaoDados.totalRegistros}")
	public Integer totalRegistros;

	@Value("${migracaoDados.gridSize}")
	public Integer gridSize;

	@Autowired
	@Qualifier("transactionManagerApp")
	private PlatformTransactionManager transactionManagerApp;

	@Bean // Representa o StepMaster
	public Step migrarDadosBancariosManager(
		ItemReader<DadosBancarios> arquivoPessoaReader,
		ItemWriter<DadosBancarios> pessoaWriter,
		Partitioner partitioner,
		TaskExecutor taskExecutor
	) {
		return stepBuilderFactory
			.get("migrarDadosBancariosStep.manager") // para representar o master
			.partitioner("migrarDadosBancariosStep", partitioner) // Nome do step que está sendo particionado + Partitioner
			.step(migrarDadosBancariosStep(arquivoPessoaReader, pessoaWriter)) // Step que vai ser particionado
			.gridSize(gridSize) // Vai dizer quantos workers serão criados. O tamanho do gridSize e do chunk precisam ser valores múltiplos. Exemplo: 10 é multiplo de 20000
			.taskExecutor(taskExecutor) // Vai dizer quantas threads estarão disponíveis para processar esses workers. Se eu colocar um gridSize de tamanho 10 e colocar 4 threads para processar os workers, então essas 4 threads vão se revezar para processar essas partições.
			.build();
	}

	// Representa o StepWorker
	public Step migrarDadosBancariosStep(ItemReader<DadosBancarios> arquivoDadosBancariosReader,
			ItemWriter<DadosBancarios> bancoDadosBancariosWriter) {
		return stepBuilderFactory
				.get("migrarDadosBancariosStep")
				.<DadosBancarios, DadosBancarios>chunk(totalRegistros)
				.reader(arquivoDadosBancariosReader)
				.writer(bancoDadosBancariosWriter)
				.transactionManager(transactionManagerApp)
				.build();
	}
}

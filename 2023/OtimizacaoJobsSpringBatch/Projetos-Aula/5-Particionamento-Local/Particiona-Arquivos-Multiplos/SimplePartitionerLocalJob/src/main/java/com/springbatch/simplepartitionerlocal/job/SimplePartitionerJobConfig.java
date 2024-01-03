package com.springbatch.simplepartitionerlocal.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class SimplePartitionerJobConfig {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Bean // Pequeno detalhe: colocar um fluxo a mais de leitura e escrita de arquivos, para subdividir um arquivo grande, não tem grande influência no tempo que o batch leva para processar. O gargalo é apenas o banco de dados por ele estar em outro local e precisa ser acessado remotamente
	public Job simplePartitionerJob(@Qualifier("migrarPessoaManager") Step migrarPessoaStep,
			@Qualifier("migrarDadosBancariosManager") Step migrarDadosBancariosStep) {
		return jobBuilderFactory.get("simplePartitionerJob")
				.start(dividirArquivosFlow(null, null)) // apenas para dividir o arquivo único em vários. Nulo por possuir qualifier
				.next(migrarPessoaStep)
				.next(migrarDadosBancariosStep)
				.end()
				.incrementer(new RunIdIncrementer())
				.build();
	}

	@Bean // A divisão do processamento por partições de múltiplos arquivos é possível restartar, pois ele pegará a linha que deu erro.
	public Flow dividirArquivosFlow(
		@Qualifier("dividirArquivoPessoaStep") Step dividirArquivoPessoaStep,
		@Qualifier("dividirArquivoDadosBancariosStep") Step dividirDadosBancariosPessoaStep) {
		return new FlowBuilder<Flow>("dividirArquivosFlow")
			.start(dividirArquivoPessoaStep)
			.next(dividirDadosBancariosPessoaStep)
			.build();
	}
}

package com.springbatch.competicao.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.competicao.dominio.Product;

import java.util.concurrent.Future;

@Configuration
public class CompeticaoStepConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	@Qualifier("transactionManagerApp")
	private PlatformTransactionManager transactionManagerApp;
	
	@Bean
	public Step competicaoStep(
			ItemReader<Product> reader,
			AsyncItemProcessor<Product, Product> processor,
			AsyncItemWriter<Product> writer) {
		return stepBuilderFactory
				.get("competicaoStep")
				.<Product, Future<Product>>chunk(1000)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.transactionManager(transactionManagerApp)
				.build();
	}
}

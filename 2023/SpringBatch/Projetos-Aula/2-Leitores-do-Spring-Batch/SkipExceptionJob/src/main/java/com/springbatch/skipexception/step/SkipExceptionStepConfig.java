package com.springbatch.skipexception.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.skipexception.dominio.Cliente;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.concurrent.Future;

@Configuration
public class SkipExceptionStepConfig {

	Logger logger = LoggerFactory.getLogger(SkipExceptionStepConfig.class);
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step skipExceptionStep(ItemReader<Cliente> skipExceptionReader, ItemWriter<Cliente> skipExceptionWriter) {
		return stepBuilderFactory
				.get("skipExceptionStep")
				.<Cliente, Future<Cliente>>chunk(13)
				.reader(skipExceptionReader)
				.processor(asyncItemProcessor())
				.writer(asyncItemWriter(skipExceptionWriter))
				.faultTolerant()
					.skipPolicy((throwable, i) -> i >= 0)
				.skip(Exception.class)
//				.taskExecutor(taskExecutor())
//				.skipLimit(2)
				.build();
	}

	private AsyncItemWriter<Cliente> asyncItemWriter(ItemWriter<Cliente> writer) {
		AsyncItemWriter<Cliente> asyncItemWriter = new AsyncItemWriter<>();
		asyncItemWriter.setDelegate(writer);
		return asyncItemWriter;
	}

	private AsyncItemProcessor<Cliente, Cliente> asyncItemProcessor() {
		AsyncItemProcessor<Cliente, Cliente> asyncItemProcessor = new AsyncItemProcessor<>();
		asyncItemProcessor.setDelegate(cliente -> {
			logger.info("Cliente dentro da processor: " + cliente);
			return cliente;
		});

		asyncItemProcessor.setTaskExecutor(taskExecutor());
		return asyncItemProcessor;
	}



	@Bean
	public TaskExecutor taskExecutor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(4);
		taskExecutor.setMaxPoolSize(4);
		taskExecutor.setQueueCapacity(8);
		taskExecutor.setThreadNamePrefix("Teste-");

		return taskExecutor;
	}
}

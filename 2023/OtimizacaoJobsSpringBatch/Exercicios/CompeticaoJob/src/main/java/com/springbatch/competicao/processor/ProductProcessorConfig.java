package com.springbatch.competicao.processor;

import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.springbatch.competicao.dominio.Product;
import com.springbatch.competicao.dominio.ProductResponse;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ProductProcessorConfig {
	private static final RestTemplate restTemplate = new RestTemplate();

	@Bean
	public AsyncItemProcessor<Product, Product> asyncProductProcessor() throws Exception {
		AsyncItemProcessor<Product, Product> asyncProductProcessor = new AsyncItemProcessor<>();
		asyncProductProcessor.setDelegate(productProcessor());
		asyncProductProcessor.setTaskExecutor(taskExecutor());
		return asyncProductProcessor;
	}
	
	@SuppressWarnings("unchecked")
	public ItemProcessor<Product, Product> productProcessor() throws Exception {
		return product -> {
			String uri = String.format("https://dummyjson.com/products/%d", product.getId());
			ResponseEntity<ProductResponse> response = restTemplate.getForEntity(uri, ProductResponse.class);
			ProductResponse newProduct = response.getBody();
			product.setTextImages(String.join(";", newProduct.getImages()));
			return product;
		};
	}

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(8);
		taskExecutor.setMaxPoolSize(8);
		taskExecutor.setQueueCapacity(8);
		taskExecutor.setThreadNamePrefix("Multithreaded-");
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return taskExecutor;
	}


}

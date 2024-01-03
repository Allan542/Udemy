package com.springbatch.competicao.writer;

import javax.sql.DataSource;

import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.competicao.dominio.Product;

@Configuration
public class ProductWriterConfig {

	@Bean
	public AsyncItemWriter<Product> asyncProductWriter(){
		AsyncItemWriter<Product> asyncProductWriter = new AsyncItemWriter<>();
		asyncProductWriter.setDelegate(productWriter(null));
		return asyncProductWriter;
	}

	@Bean
	public ItemWriter<Product> productWriter(@Qualifier("appDataSource") DataSource dataSource) {		
		return new JdbcBatchItemWriterBuilder<Product>()
				.dataSource(dataSource)
				.sql("INSERT INTO new_products (id, name, description, thumbnail, price, discount_amount, status, images) VALUES (:id, :name, :description, :thumbnail, :price, :discountAmount, :status, :textImages)")
				.beanMapped()
				.build();
	}
}

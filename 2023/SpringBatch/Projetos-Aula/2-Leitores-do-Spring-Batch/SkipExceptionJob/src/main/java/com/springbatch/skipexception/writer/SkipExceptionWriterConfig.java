package com.springbatch.skipexception.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.skipexception.dominio.Cliente;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Configuration
public class SkipExceptionWriterConfig  {

	Logger logger = LoggerFactory.getLogger(SkipExceptionWriterConfig.class);

	@Bean
	public JdbcBatchItemWriter<Cliente> jdbcBatchItemWriter(
		@Qualifier("appDataSource") DataSource dataSource
	){
		return new JdbcBatchItemWriterBuilder<Cliente>()
			.dataSource(dataSource)
			.sql("INSERT INTO tabela_skip VALUES (?, ?)")
			.itemPreparedStatementSetter(new ItemPreparedStatementSetter<Cliente>() {
				@Override
				public void setValues(Cliente cliente, PreparedStatement preparedStatement) throws SQLException {
					if(cliente.getNome().equals("Fernando")){
						throw new SQLException();
					}
//					logger.info("Cliente dentro da writer: " + cliente);
					preparedStatement.setString(1, cliente.getNome());
					preparedStatement.setString(2, cliente.getSobrenome());

				}
			})
			.build();
	}
}

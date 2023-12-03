package com.springbatch.contasbancarias.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.contasbancarias.dominio.Conta;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class JdbcContaWriterConfig {
	@Bean // Ele é um batch que realiza a escrita em lote, ou seja, ele obtém o insert e todos os objetos a serem escritos na mesma transação que foi aberta e não inserir de um em um
	public JdbcBatchItemWriter<Conta> jdbcContaWriter(
		@Qualifier("appDataSource")DataSource dataSource
		) {
		return new JdbcBatchItemWriterBuilder<Conta>()
			.dataSource(dataSource)
			.sql("INSERT INTO conta (tipo, limite, cliente_id) VALUES (?, ?, ?)")
			.itemPreparedStatementSetter(itemPreparedStatementSetter())
			.build();
	}

	private ItemPreparedStatementSetter<Conta> itemPreparedStatementSetter() {
		return new ItemPreparedStatementSetter<Conta>() {
			@Override
			public void setValues(Conta conta, PreparedStatement ps) throws SQLException {
				ps.setString(1, conta.getTipo().name());
				ps.setDouble(2, conta.getLimite());
				ps.setString(3, conta.getClienteId());
			}
		};
	}
}

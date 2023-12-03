package com.springbatch.jdbccursorreader.reader;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.jdbccursorreader.dominio.Cliente;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
public class JdbcCursorReaderConfig {
	@Bean
	public JdbcCursorItemReader<Cliente> jdbcCursorReader(
		@Qualifier("appDataSource") DataSource dataSource // appDataSource é um nome padrão que o spring colocará no nosso banco de dados da aplicação
		) {
		return new JdbcCursorItemReaderBuilder<Cliente>()
			.name("jdbcCursorReader")
			.dataSource(dataSource)
			.sql("select * from cliente") // SQL que buscará os clientes
			.rowMapper(new BeanPropertyRowMapper<>(Cliente.class)) // Mapeia os campos das linhas da tabela se a classe informada para servir como mapeamento tiver como nome dos atributos, o mesmo nome dos campos dos dados
			.build();
	}
}

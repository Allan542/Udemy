package com.udemy.contasbancariasjob.reader;

import com.udemy.contasbancariasjob.dominio.Cliente;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
public class ContasBancariasJdbcReaderConfig {

    @Bean
    public JdbcCursorItemReader<Cliente> contasBancariasJdbcReader(
        @Qualifier("appDataSource") DataSource dataSource
    ){
        return new JdbcCursorItemReaderBuilder<Cliente>()
            .name("contasBancariasJdbcReader")
            .dataSource(dataSource)
            .sql("select * from cliente order by faixa_salarial")
            .rowMapper(new BeanPropertyRowMapper(Cliente.class))
            .build();
    }
}

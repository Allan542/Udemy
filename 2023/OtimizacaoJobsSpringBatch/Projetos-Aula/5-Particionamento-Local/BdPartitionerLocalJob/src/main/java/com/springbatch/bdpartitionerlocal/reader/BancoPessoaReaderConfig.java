package com.springbatch.bdpartitionerlocal.reader;

import javax.sql.DataSource;

import com.springbatch.bdpartitionerlocal.dominio.DadosBancarios;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.bdpartitionerlocal.dominio.Pessoa;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Configuration
public class BancoPessoaReaderConfig {

  @StepScope // Se algum bean que este método recebe está anotado com StepScope para poder pegar o contexto de execução, este metódo também precisa estar anotado como StepScope
  @Bean
  public JdbcPagingItemReader<Pessoa> pessoaReader(
      @Qualifier("appDataSource") DataSource dataSource,
      @Qualifier("queryProviderPessoa") PagingQueryProvider queryProvider) {
    return new JdbcPagingItemReaderBuilder<Pessoa>()
        .name("pessoaReader")
        .dataSource(dataSource)
        .queryProvider(queryProvider)
        .pageSize(2000)
        .rowMapper(new BeanPropertyRowMapper<>(Pessoa.class))
        .build();
  }

  @StepScope
  @Bean
  public SqlPagingQueryProviderFactoryBean queryProviderPessoa(
      @Qualifier("appDataSource") DataSource dataSource,
      @Value("#{stepExecutionContext['minValue']}") Long minValue,
      @Value("#{stepExecutionContext['maxValue']}") Long maxValue
  ) {
    SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
    queryProvider.setSelectClause("*");
    queryProvider.setFromClause("from pessoa_origem");
    queryProvider.setWhereClause("where id >=" + minValue + " and " + "id <= " + maxValue);
    queryProvider.setSortKey("id");
    queryProvider.setDataSource(dataSource);
    return queryProvider;
  }
}

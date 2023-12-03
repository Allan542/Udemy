package com.springbatch.folhaponto.writer;

import com.springbatch.folhaponto.dominio.FolhaPonto;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class JdbcFolhaPontoWriterConfig {

    @Bean
    public JdbcBatchItemWriter<FolhaPonto> jdbcFolhaPontoWriter(
        @Qualifier("appDataSource") DataSource dataSource
        ){
        return new JdbcBatchItemWriterBuilder<FolhaPonto>()
            .dataSource(dataSource)
            .sql("INSERT INTO folha_ponto (mes, ano, funcionario_id, registros_ponto) VALUES (?, ?, ?, ?)")
            .itemPreparedStatementSetter(itemPreparedStatementSetter())
            .build();
    }

    private ItemPreparedStatementSetter<FolhaPonto> itemPreparedStatementSetter() {
        return (folhaPonto, preparedStatement) -> {
            preparedStatement.setInt(1, folhaPonto.getMes());
            preparedStatement.setInt(2, folhaPonto.getAno());
            preparedStatement.setInt(3, folhaPonto.getMatricula());
            preparedStatement.setString(4, folhaPonto.getRegistrosPontoTexto());
        };
    }
}

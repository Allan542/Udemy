package com.springbatch.contasbancarias.writer;

import com.springbatch.contasbancarias.dominio.Conta;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompositeContaWriterConfig {

    @Bean // Componente do spring batch que cria um escritor composto por outros, ou seja, ele delega a escrita para outros escritores
    public CompositeItemWriter<Conta> compositeContaWriter(
        @Qualifier("fileContaWriter") FlatFileItemWriter<Conta> flatFileItemWriter, // Como existe dois Bean de FlatFileItemWriter, a gente precisa usar o @Qualifier para o Spring entender qual ele deve usar
        JdbcBatchItemWriter<Conta> jdbcBatchItemWriter
    ) {
        return new CompositeItemWriterBuilder<Conta>()
            .delegates(flatFileItemWriter, jdbcBatchItemWriter)
            .build();
    }
}

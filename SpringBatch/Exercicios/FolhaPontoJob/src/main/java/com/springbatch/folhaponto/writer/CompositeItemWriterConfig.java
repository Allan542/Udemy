package com.springbatch.folhaponto.writer;

import com.springbatch.folhaponto.dominio.FolhaPonto;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompositeItemWriterConfig {

    @Bean
    public CompositeItemWriter<FolhaPonto> compositeFolhaPontoWriter(
        @Qualifier("imprimeFolhaPontoWriter") FlatFileItemWriter<FolhaPonto> fileItemWriter,
        JdbcBatchItemWriter<FolhaPonto> jdbcBatchItemWriter
    ){
        return new CompositeItemWriterBuilder<FolhaPonto>()
            .delegates(fileItemWriter, jdbcBatchItemWriter)
            .build();
    }
}

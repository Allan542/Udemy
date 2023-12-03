package com.springbatch.folhaponto.writer;

import com.springbatch.folhaponto.dominio.FolhaPonto;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FolhaPontoWriterConfig {

    @Bean
    public ClassifierCompositeItemWriter<FolhaPonto> classifierFolhaPonto(
        CompositeItemWriter<FolhaPonto> compositeItemWriter,
        @Qualifier("imprimeFolhaPontoInvalido") FlatFileItemWriter<FolhaPonto> folhaPontoInvalidoWriter
    ){
        return new ClassifierCompositeItemWriterBuilder<FolhaPonto>()
            .classifier(classifier(compositeItemWriter, folhaPontoInvalidoWriter))
            .build();
    }

    private Classifier<FolhaPonto, ItemWriter<? super FolhaPonto>> classifier(
        CompositeItemWriter<FolhaPonto> compositeItemWriter,
        FlatFileItemWriter<FolhaPonto> folhaPontoInvalidoWriter
    ) {
        return folhaPonto -> {
            if(folhaPonto.getRegistrosPontos().isEmpty())
                return folhaPontoInvalidoWriter;
            return compositeItemWriter;
        };
    }
}

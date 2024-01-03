package com.springbatch.simplepartitionerlocal.config;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class MultiResourcePartitionerConfig {
    @Bean
    @StepScope // Resumo do que esse particionador faz: Ele tem os arquivos, ele sabe qual identificador colocar, então ele vai colocar uma partição para cada arquivo
    public MultiResourcePartitioner pessoaPartitioner(
        @Value("file:files/pessoas-tmp*") Resource[] resources
        ) {
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        partitioner.setKeyName("file"); // vai ser o identificador colocado no contexto de execução para que o leitor possa saber qual arquivo ele lerá. É daí que ele vai pegar o nome do arquivo/o próprio arquivo e ler
        partitioner.setResources(resources);
        return partitioner;
    }

    @Bean
    @StepScope // Resumo do que esse particionador faz: Ele tem os arquivos, ele sabe qual identificador colocar, então ele vai colocar uma partição para cada arquivo
    public MultiResourcePartitioner dadosBancariosPartitioner(
        @Value("file:files/dados_bancarios-tmp*") Resource[] resources
    ) {
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        partitioner.setKeyName("file"); // vai ser o identificador colocado no contexto de execução para que o leitor possa saber qual arquivo ele lerá. É daí que ele vai pegar o nome do arquivo e ler
        partitioner.setResources(resources);
        return partitioner;
    }
}

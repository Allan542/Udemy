package com.springbatch.arquivodelimitado.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.arquivodelimitado.dominio.Cliente;
import org.springframework.core.io.Resource;

@Configuration
public class LeituraArquivoDelimitadoReaderConfig {

	@StepScope
	@Bean
	public FlatFileItemReader<Cliente> leituraArquivoDelimitadoReader(
		@Value("#{jobParameters['arquivoClientes']}") Resource arquivoClientes
	) {
		return new FlatFileItemReaderBuilder<Cliente>()
			.name("leituraArquivoDelimitadoReader") // nome do reader
			.resource(arquivoClientes) // recurso que ele vai ler
			.delimited() // tipo do arquivo, no caso, delimitado (por vírgula)
			.names("nome", "sobrenome", "idade", "email") // nomes das propriedades
			.targetType(Cliente.class) // Mapeia as propriedades na classe-domínio Cliente
			.build();
    }
}

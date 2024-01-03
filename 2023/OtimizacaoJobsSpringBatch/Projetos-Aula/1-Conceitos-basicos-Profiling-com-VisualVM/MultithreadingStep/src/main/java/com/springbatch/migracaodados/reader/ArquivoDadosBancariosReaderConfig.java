package com.springbatch.migracaodados.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.springbatch.migracaodados.dominio.DadosBancarios;

@Configuration
public class ArquivoDadosBancariosReaderConfig {
	@Bean
	public FlatFileItemReader<DadosBancarios> dadosBancariosReader() {
		return new FlatFileItemReaderBuilder<DadosBancarios>()
				.name("dadosBancariosReader")
				.resource(new FileSystemResource("files/dados_bancarios.csv"))
				.delimited()
				.names("pessoaId", "agencia", "conta", "banco", "id")
				.addComment("--")
				.saveState(false) // Não pode salvar estado no reader, por não serem threadSafe (Uma thread modificar o valor de algo que outra thread pode, poderá utilizar) e também, porque não é possível restartar o job. Não podemos salvar estado, para caso ocorra um erro no meio do processamento, o Spring não tente reiniciar o job com dados inconsistentes de acordo com os metadados salvos e não conseguiria de fato restartá-lo
				.targetType(DadosBancarios.class)
				.build();
	}
}

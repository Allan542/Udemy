package com.springbatch.simplepartitionerlocal.reader;

import com.springbatch.simplepartitionerlocal.config.ArquivoPartitioner;
import com.springbatch.simplepartitionerlocal.dominio.Pessoa;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.springbatch.simplepartitionerlocal.dominio.DadosBancarios;

@Configuration
public class ArquivoDadosBancariosReaderConfig {

	@Autowired
	private ArquivoPartitioner partitioner;

	@StepScope
	// Para usar o @Value dentro de um bean, é necessário o stepScope. Ele também vai ser usado pelo motivo das partições estarem utilizando arquivos. Então há chances de alguma fechar o recurso de leitura antes do esperado. Com o StepScope, isso só vai ser encerrado quando o StepManager for encerrado, no final dele
	@Bean // O leitor precisa conhecer este limite, pois é assim que ele vai identificar quantos itens ele precisa ler por partição
	public CustomArquivoReader<DadosBancarios> dadosBancariosReader(
		@Value("#{stepExecutionContext['particao']}") Integer particao // Pegar o valor de index da partição a partir do ExecutionContext
	){
		return new CustomArquivoReader<>(
			dadosBancariosReader(partitioner.calcularPrimeiroItemleitura(particao)), // Chama a função para calcular de qual linha do arquivo, o leitor de cada partição (variável particao que é recebida nesse método a partir do ExecutionContext e mandada para esta função que definirá o cálculo) deve começar
			partitioner.getItensLimit());
	}

	public FlatFileItemReader<DadosBancarios> dadosBancariosReader(int currentItemCount) {
		return new FlatFileItemReaderBuilder<DadosBancarios>()
				.name("dadosBancariosReader")
				.resource(new FileSystemResource("files/dados_bancarios.csv"))
				.delimited()
				.names("pessoaId", "agencia", "conta", "banco", "id")
				.addComment("--")
				.currentItemCount(currentItemCount)
				.targetType(DadosBancarios.class)
				.build();
	}
}

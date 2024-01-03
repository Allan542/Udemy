package com.springbatch.simplepartitionerlocal.reader;

import java.util.Date;

import com.springbatch.simplepartitionerlocal.config.ArquivoPartitioner;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.BindException;

import com.springbatch.simplepartitionerlocal.dominio.Pessoa;

@Configuration
public class ArquivoPessoaReaderConfig {

	@Autowired
	private ArquivoPartitioner partitioner;

	@StepScope // Para usar o @Value dentro de um bean, é necessário o stepScope. Ele também vai ser usado pelo motivo das partições estarem utilizando arquivos. Então há chances de alguma fechar o recurso de leitura antes do esperado. Com o StepScope, isso só vai ser encerrado quando o StepManager for encerrado, no final dele
	@Bean // O leitor precisa conhecer este limite, pois é assim que ele vai identificar quantos itens ele precisa ler por partição
	public CustomArquivoReader<Pessoa> arquivoPessoaReader(
		@Value("#{stepExecutionContext['particao']}") Integer particao // Pegar o valor de index da partição a partir do ExecutionContext
	){
		return new CustomArquivoReader<Pessoa>(
			arquivoPessoaReader(partitioner.calcularPrimeiroItemleitura(particao)), // Chama a função para calcular de qual linha do arquivo, o leitor de cada partição (variável particao que é recebida nesse método a partir do ExecutionContext e mandada para esta função que definirá o cálculo) deve começar
			partitioner.getItensLimit());
	}

	public FlatFileItemReader<Pessoa> arquivoPessoaReader(int currentItemCount) {
		return new FlatFileItemReaderBuilder<Pessoa>()
				.name("arquivoPessoaReader")
				.resource(new FileSystemResource("files/pessoas.csv"))
				.delimited()
				.names("nome", "email", "dataNascimento", "idade", "id")
				.addComment("--")
				.currentItemCount(currentItemCount) // Definindo de onde a leitura vai começar. Ex.: Partição1: 0, Partição2: 2000, Partição 3: 4000, e assim sucessivamente.
				.fieldSetMapper(fieldSetMapper())
				.build();
	}

	private FieldSetMapper<Pessoa> fieldSetMapper() {
		return new FieldSetMapper<Pessoa>() {

			@Override
			public Pessoa mapFieldSet(FieldSet fieldSet) throws BindException {
				Pessoa pessoa = new Pessoa();
				pessoa.setNome(fieldSet.readString("nome"));
				pessoa.setEmail(fieldSet.readString("email"));
				pessoa.setDataNascimento(new Date(fieldSet.readDate("dataNascimento", "yyyy-MM-dd HH:mm:ss").getTime()));
				pessoa.setIdade(fieldSet.readInt("idade"));
				pessoa.setId(fieldSet.readInt("id"));
				return pessoa;
			}
		};
	}
}

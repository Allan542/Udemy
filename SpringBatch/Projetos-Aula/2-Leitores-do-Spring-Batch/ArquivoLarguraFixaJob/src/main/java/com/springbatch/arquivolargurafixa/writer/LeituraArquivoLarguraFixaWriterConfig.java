package com.springbatch.arquivolargurafixa.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.arquivolargurafixa.dominio.Cliente;

@Configuration
public class LeituraArquivoLarguraFixaWriterConfig {
	@Bean
	public ItemWriter<Cliente> leituraArquivoLarguraFixaWriter() {
		// Para este caso, se o tamanho do chunk for colocado para um número maior que a quantidade dados e houver uma falha na metade do processo, ele lê os arquivos, mas não commita a transação
		// Por ter parado no meio dela, já que cada chunk é uma transação diferente e começaria uma nova transação do zero mesmo que a outra tenha sido lida em partes.
		// E também, a gente perde em termos de restartabilidade, já que apesar de lidos para nós, o batch não entendeu que os dados da transação já foram lidos por estarem dentro de um único chunk.
		// Por isso que é bom equilibrar o número de chunks, para caso haja uma falha no meio do processo, o batch saiba se virar e começar mais ou menos da onde ele parou, sem ter que começar tudo de novo.
		// Um bom exemplo disso seria um arquivo com 10000 linhas, com um chunkSize de 200 por transação. Ele deve parar apenas quando uma das transações falharem e recomeçar dessa última transação que falhou sem afetar as anteriores.
		return items -> items.forEach(System.out::println);
//		return items -> {
//			for (Cliente cliente: items) {
//				if(cliente.getNome().equals("Maria")){
//					throw new Exception();
//				} else {
//					System.out.println(cliente);
//				}
//			}
//		};
	}
}

package com.springbatch.arquivomultiplosformatos.reader;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;
import com.springbatch.arquivomultiplosformatos.dominio.Transacao;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ClienteTransacaoLineMapperConfig {

    // Não é informado tipos dentro de todos os métodos porque não temos o tipo exato que está sendo lido
    @SuppressWarnings("rawtypes")
    @Bean // Este tipo de componente é capaz de usar um padrão para saber qual tipo de LineMapper ele vai usar. Por isso que ele é composto, por possuir vários LineMappers.
    // Dependendo do padrão que está lendo, ele vai escolher qual lineMapper vai ser aplicado
    public PatternMatchingCompositeLineMapper lineMapper() {
        PatternMatchingCompositeLineMapper lineMapper = new PatternMatchingCompositeLineMapper();
        lineMapper.setTokenizers(tokenizers()); // Tokenizers pegam as linhas e dividem elas em palavras
        lineMapper.setFieldSetMappers(fieldSetMappers()); // FieldSetMappers pega essas palavras e mapeia para o objeto de domínio
        return lineMapper;
    }

    @SuppressWarnings("rawtypes") // Segue o mesmo padrão de um LineTokenizer, que é a String com o padrão do tipo da linha e
    // o FieldSetMapper que serve para mapear a linha em um objeto especificado de acordo com o seu padrão
    private Map<String, FieldSetMapper> fieldSetMappers() {
        Map<String, FieldSetMapper> fieldSetMappers = new HashMap<>();
        // A lógica para o mapeamento do tipo em objeto é o mesmo, então é necessário apenas um método
        fieldSetMappers.put("0*", fieldSetMapper(Cliente.class)); // Mapeando o tipo 0 para o objeto do tipo cliente
        fieldSetMappers.put("1*", fieldSetMapper(Transacao.class)); // Mapeando o tipo 0 para o objeto do tipo transação
        return fieldSetMappers;
    }

    @SuppressWarnings("rawtypes") // FieldSetMappers é equivalente ao targetType() de um FlatFileItemReaderBuilder
    private FieldSetMapper fieldSetMapper(Class classe) {
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper(); // BeanWrapper que já faz o mapeamento do tipo da linha para o objeto especificado
        fieldSetMapper.setTargetType(classe); // Recebe o objeto que deseja que os dados sejam passados
        return fieldSetMapper;
    }

    // o Map tem a identificação do padrão que está sendo usado para tokenizar (ou transformar a linha em palavra) a linha que é a String
    // e o LineTokenizer que é o transformador de linha em palavra. Quando for o padrão x, vai ser usado o LineTokenizer x. Quando for o padrão y, vai ser usado o LineTokenizer y.

    // Tokenizers é equivalente ao names() de um FlatFileItemReaderBuilder
    private Map<String, LineTokenizer> tokenizers() {
        Map<String, LineTokenizer> tokenizers = new HashMap<>();
        tokenizers.put("0*", clienteLineTokenizer()); // 0 seguido de qualquer coisa(*)
        tokenizers.put("1*", transacaoLineTokenizer()); // 1 seguido de qualquer coisa(*)
        return tokenizers;
    }

    private LineTokenizer clienteLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(); // O arquivo é do tipo delimitado (separado por vírgula), então é usado este cara
        lineTokenizer.setNames("nome", "sobrenome", "idade", "email"); // Significado de cada propriedade do padrão Cliente
        lineTokenizer.setIncludedFields(1, 2, 3, 4); // Sempre pulando o primeiro porque ele apenas significa o tipo da linha (no caso, Cliente), então não precisa mapeá-lo
        return lineTokenizer;
    }

    private LineTokenizer transacaoLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(); // O arquivo é do tipo delimitado (separado por vírgula), então é usado este cara
        lineTokenizer.setNames("id", "descricao", "valor"); // Significado de cada propriedade do padrão Transação
        lineTokenizer.setIncludedFields(1, 2, 3); // Sempre pulando o primeiro porque ele apenas significa o tipo da linha (no caso, Transação), então não precisa mapeá-lo
        return lineTokenizer;
    }
}

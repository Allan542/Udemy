package com.springbatch.arquivomultiplosformatos.reader;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;
import com.springbatch.arquivomultiplosformatos.dominio.Transacao;
import org.springframework.batch.item.*;

public class ArquivoClienteTransacaoReader implements ItemStreamReader<Cliente> {
    private Object objAtual;
    // Atributo que implementa o padrão delegate. O padrão delegate informa que você vai utilizar um objeto e delegar a execução de alguma rotina pra ele.
    // Como a leitura do arquivo é uma lógica já implementada, a gente vai delegar esta leitura para o objeto que já existe.
    // O delegate precisa ser uma classe que implementa a classe informada
    private final ItemStreamReader<Object> delegate;

    // Inicializando o leitor
    public ArquivoClienteTransacaoReader(ItemStreamReader<Object> delegate){
        this.delegate = delegate;
    }

    @Override
    public Cliente read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if(objAtual == null){ // Se é a primeira vez que carrega o objeto, lê o primeiro objeto
            objAtual = delegate.read();
        }

        // Passa o objeto para cliente e esvazia o objeto
        Cliente cliente = (Cliente) objAtual;
        objAtual = null;

        // Se retornou um cliente
        if (cliente != null) { // Faz um: enquanto espiar algo que for uma transação/do tipo transação
            while (peek() instanceof Transacao){
                cliente.getTransacoes().add((Transacao) objAtual); // adiciona na lista as transações que o cliente possui até que o próximo seja um cliente novamente
            }
        }
        return cliente;
    }

    private Object peek() throws Exception {
        objAtual = delegate.read(); // lê uma transação a partir do leitor delegado, que é a leitura do próximo item
        return objAtual;
    }

    @Override // Os outros métodos não precisam implementar, já que a própria classe delegada implementa
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegate.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        delegate.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        delegate.close();
    }
}

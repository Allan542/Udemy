package com.springbatch.processadorclassifier.processor;

import com.springbatch.processadorclassifier.dominio.Cliente;
import org.springframework.batch.item.ItemProcessor;

public class ClienteProcessor implements ItemProcessor<Cliente, Cliente> { // Por estar no contexto do cliente, os tipos podem ser definidos

    @Override
    public Cliente process(Cliente cliente) throws Exception {
        System.out.println(String.format("\nAplicando regras de negócio no cliente %s", cliente.getEmail()));
        return cliente;
    }
}

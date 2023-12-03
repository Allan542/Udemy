package com.springbatch.processadorclassifier.processor;

import com.springbatch.processadorclassifier.dominio.Transacao;
import org.springframework.batch.item.ItemProcessor;

public class TransacaoProcessor implements ItemProcessor<Transacao, Transacao> { // Por estar no contexto da transação, os tipos podem ser definidos
    @Override
    public Transacao process(Transacao transacao) throws Exception {
        System.out.println(String.format("\nAplicando regras de negócio na transação %s", transacao.getId()));
        return transacao;
    }
}

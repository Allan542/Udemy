package com.springbatch.simplepartitionerlocal.reader;

import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.stereotype.Component;

public class CustomArquivoReader<T> implements ItemStreamReader<T> {

    private FlatFileItemReader<T> delegate;
    private int itensLimit;

    public CustomArquivoReader(FlatFileItemReader<T> delegate, int itensLimit){
        this.delegate = delegate;
        this.itensLimit = itensLimit;
    }


    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if(itensLimit > 0) { // Se ainda tem coisas para ler dentro desse itensLimit, ele lê decrementa e lê o próximo. Senão, ele encerra o leitor (joga um nulo)
            itensLimit--;
            return delegate.read();
        }
        return null;
    }

    @Override
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

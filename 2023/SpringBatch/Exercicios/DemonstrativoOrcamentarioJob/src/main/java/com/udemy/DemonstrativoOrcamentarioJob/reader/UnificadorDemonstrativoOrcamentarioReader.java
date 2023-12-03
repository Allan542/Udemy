package com.udemy.DemonstrativoOrcamentarioJob.reader;

import com.udemy.DemonstrativoOrcamentarioJob.dominio.DemonstrativoOrcamentario;
import com.udemy.DemonstrativoOrcamentarioJob.dominio.DemonstrativoLancamento;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

public class UnificadorDemonstrativoOrcamentarioReader implements ItemStreamReader<DemonstrativoOrcamentario>, ResourceAwareItemReaderItemStream<DemonstrativoOrcamentario> {

    private DemonstrativoOrcamentario demonstrativoAtual;

    private FlatFileItemReader<DemonstrativoOrcamentario> delegate;

    public UnificadorDemonstrativoOrcamentarioReader(FlatFileItemReader<DemonstrativoOrcamentario> delegate){
        this.delegate = delegate;
    }

    @Override
    public DemonstrativoOrcamentario read() throws Exception {
        if(demonstrativoAtual == null){
            demonstrativoAtual = delegate.read();
        }

        DemonstrativoOrcamentario demonstrativoPrimario = demonstrativoAtual;

        if(demonstrativoPrimario != null){
            while (true){
                demonstrativoAtual = delegate.read();
                DemonstrativoOrcamentario demonstrativoSecundario = demonstrativoAtual;
                if(demonstrativoSecundario == null || !demonstrativoPrimario.getCodigoNaturezaDespesa().equals(demonstrativoSecundario.getCodigoNaturezaDespesa())){
                    break;
                }

                DemonstrativoLancamento demonstrativoLancamento = demonstrativoSecundario.getDemonstrativoList().get(0);
                demonstrativoPrimario.getDemonstrativoList().add(demonstrativoLancamento);
            }
        }

        return demonstrativoPrimario;
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

    @Override
    public void setResource(Resource resource) {
        delegate.setResource(resource);
    }
}

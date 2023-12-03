package com.springbatch.demonstrativoorcamentario.writer;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.List;

@Component
public class DemonstrativoOrcamentarioRodape implements FlatFileFooterCallback {

    private Double totalGeral = 0.0;

    @Override
    public void writeFooter(Writer writer) throws IOException {
        writer.append("\n");
        writer.append(String.format("\t\t\t\t\t\t\t  Total: %s\n", NumberFormat.getCurrencyInstance().format(totalGeral)));
        writer.append(String.format("\t\t\t\t\t\t\t  Código de Autenticação: %s", "fkyew6868fewjfhjjewf"));
    }

    @BeforeWrite // Escuta eventos antes de um writer para capturar os valores e fazer a soma do total
    public void beforeWrite(List<GrupoLancamento> grupos) {
        for (GrupoLancamento grupoLancamento : grupos) {
            totalGeral += grupoLancamento.getTotal();
        }
    }

    @AfterChunk // Sempre depois que terminar uma transação (chunk), ele zerará o valor do totalGeral
    public void afterChunk(ChunkContext context) {
        totalGeral = 0.0;
    }

}

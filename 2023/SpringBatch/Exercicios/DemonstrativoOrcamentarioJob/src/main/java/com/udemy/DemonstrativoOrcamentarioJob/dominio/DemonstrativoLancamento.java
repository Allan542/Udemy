package com.udemy.DemonstrativoOrcamentarioJob.dominio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DemonstrativoLancamento {

    private String descricaoLancamento;
    private String dataLancamento;
    private BigDecimal valorLancamento;

    public DemonstrativoLancamento(){

    }

    public DemonstrativoLancamento(String descricaoLancamento, String dataLancamento, BigDecimal valorLancamento) {
        this.descricaoLancamento = descricaoLancamento;
        this.dataLancamento = dataLancamento;
        this.valorLancamento = valorLancamento;
    }

    public String getDescricaoLancamento() {
        return descricaoLancamento;
    }

    public void setDescricaoLancamento(String descricaoLancamento) {
        this.descricaoLancamento = descricaoLancamento;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public BigDecimal getValorLancamento() {
        return valorLancamento;
    }

    public void setValorLancamento(BigDecimal valorLancamento) {
        this.valorLancamento = valorLancamento;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataFormatada = LocalDate.parse(dataLancamento);
        return String.format("[%s] %s - R$ %,3.2f", formatter.format(dataFormatada), descricaoLancamento, valorLancamento);
    }
}

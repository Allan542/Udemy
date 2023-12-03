package com.udemy.DemonstrativoOrcamentarioJob.dominio;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class DemonstrativoOrcamentario {
    private String codigoNaturezaDespesa;
    private String descricaoNaturezaDespesa;
    private List<DemonstrativoLancamento> demonstrativoList;

    public List<DemonstrativoLancamento> getDemonstrativoList() {
        return demonstrativoList;
    }

    public void setDemonstrativoList(List<DemonstrativoLancamento> demonstrativoList) {
        this.demonstrativoList = demonstrativoList;
    }

    public String getCodigoNaturezaDespesa() {
        return codigoNaturezaDespesa;
    }

    public void setCodigoNaturezaDespesa(String codigoNaturezaDespesa) {
        this.codigoNaturezaDespesa = codigoNaturezaDespesa;
    }

    public String getDescricaoNaturezaDespesa() {
        return descricaoNaturezaDespesa;
    }

    public void setDescricaoNaturezaDespesa(String descricaoNaturezaDespesa) {
        this.descricaoNaturezaDespesa = descricaoNaturezaDespesa;
    }

    @Override
    public String toString() {

        StringBuilder unirItensDemonstrativos = new StringBuilder();
        BigDecimal valorTotal = BigDecimal.ZERO;
        demonstrativoList.sort(Comparator.comparing(DemonstrativoLancamento::getValorLancamento));
        for (DemonstrativoLancamento demonstrativoLancamento : demonstrativoList) {
            valorTotal = valorTotal.add(demonstrativoLancamento.getValorLancamento());
            unirItensDemonstrativos.append("\n\t").append(demonstrativoLancamento);
        }

        return "---- Demonstrativo orçamentário ----\n" +
            String.format("[%s] %s - R$ %,.2f", codigoNaturezaDespesa, descricaoNaturezaDespesa, valorTotal) +
            unirItensDemonstrativos;
    }
}

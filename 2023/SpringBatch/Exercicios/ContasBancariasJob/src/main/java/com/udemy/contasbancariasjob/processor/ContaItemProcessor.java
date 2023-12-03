package com.udemy.contasbancariasjob.processor;

import com.udemy.contasbancariasjob.dominio.Cliente;
import com.udemy.contasbancariasjob.dominio.Conta;
import com.udemy.contasbancariasjob.enums.TipoContaEnum;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;

public class ContaItemProcessor implements ItemProcessor<Cliente, Conta> {

    @Override
    public Conta process(Cliente cliente) {
        BigDecimal faixaSalarial = cliente.getFaixaSalarial();
        if (faixaSalarial.compareTo(new BigDecimal("3000")) <= 0){
            return Conta.builder()
                .tipoConta(TipoContaEnum.PRATA)
                .limite(new BigDecimal("500.00"))
                .clientId(cliente.getEmail())
                .build();
        } else if (faixaSalarial.compareTo(new BigDecimal("5000")) <= 0) {
            return Conta.builder()
                .tipoConta(TipoContaEnum.OURO)
                .limite(new BigDecimal("1000.00"))
                .clientId(cliente.getEmail())
                .build();
        } else if (faixaSalarial.compareTo(new BigDecimal("10000")) <= 0) {
            return Conta.builder()
                .tipoConta(TipoContaEnum.PLATINA)
                .limite(new BigDecimal("2500.00"))
                .clientId(cliente.getEmail())
                .build();
        } else {
            return Conta.builder()
                .tipoConta(TipoContaEnum.DIAMANTE)
                .limite(new BigDecimal("5000.00"))
                .clientId(cliente.getEmail())
                .build();
        }
    }
}

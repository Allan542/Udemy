package com.udemy.contasbancariasjob.dominio;

import com.udemy.contasbancariasjob.enums.TipoContaEnum;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conta {
    private TipoContaEnum tipoConta;
    private BigDecimal limite;
    private String clientId;

    @Override
    public String toString() {
        return "Conta{" +
            "tipoConta=" + tipoConta +
            String.format(", limite=R$ %,.2f", limite) +
            ", clientId='" + clientId + '\'' +
            '}';
    }
}

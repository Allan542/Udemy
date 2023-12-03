package com.udemy.contasbancariasjob.dominio;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @NotBlank
    private String nome;

    @Range(min = 18, max = 120)
    @NotNull
    private Integer idade;
    @NotBlank
    @Size(min = 6, max = 50)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email inválido")
    private String email;

    @NotNull
    private BigDecimal faixaSalarial;

    @Override
    public String toString() {
        return "Cliente{" +
            "nome='" + nome + '\'' +
            ", idade=" + idade +
            ", email='" + email + '\'' +
            String.format(", faixaSalarial=R$ %,.2f", faixaSalarial) +
            '}';
    }
}

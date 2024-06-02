package io.github.allan542.quarkussocial.domain.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users") // Dica: Há um problema ao extender o PanacheEntity, que seria a existência de um ID já criado dentro desta classe abstrata que não é possível modificar e ele não tem a estratégia de auto_increment. O melhor a se fazer é usar a classe que o PanacheEntity extende, que é a PanacheEntityBase
@Data
public class User /*extends PanacheEntityBase*/ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
}

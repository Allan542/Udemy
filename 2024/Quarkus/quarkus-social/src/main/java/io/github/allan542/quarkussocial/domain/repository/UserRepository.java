package io.github.allan542.quarkussocial.domain.repository;

import io.github.allan542.quarkussocial.domain.user.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped // Fala que esta classe está no escopo de aplicação, ou seja, uma classe dessa é como se fosse um singleton. Não importa quantos usuários tenham na API, ela vai ser só uma classe pra tudo que está manipulando usuários; vai criar uma instância do repositório dentro do contexto da aplicação, dentro do ingestor de dependências para utilizar aonde eu quiser
public class UserRepository implements PanacheRepository<User> {
}

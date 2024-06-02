package io.github.allan542.quarkussocial.rest;


import io.github.allan542.quarkussocial.domain.repository.UserRepository;
import io.github.allan542.quarkussocial.domain.user.User;
import io.github.allan542.quarkussocial.rest.dto.CreateUserRequest;
import io.github.allan542.quarkussocial.rest.dto.ResponseError;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Set;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserRepository repository;
    private Validator validator;


    @Inject // Pode injetar tanto no construtor, como direto na própria propriedade
    public UserResource(UserRepository repository, Validator validator){
        this.repository = repository;
        this.validator = validator;
    }

    @POST
    @Transactional
    public Response createUser(CreateUserRequest userRequest) {
        // Comandos do PanacheEntity que está sendo extendido pela entidade User
//        User.count();
//        user.delete();
//        User.delete("delete from User where age < 18");
//        user.persist();

        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(userRequest);
        if(!violations.isEmpty()){ // O código correto para erros de validação, sem contar a sintaxe do JSON, é 422. Um erro que entende que a sintaxe está correta e que ele entende a entidade, mas que tem erros de validação
            return ResponseError.createFromValidation(violations).withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        User user = new User();
        user.setAge(userRequest.getAge());
        user.setName(userRequest.getName());

        repository.persist(user);

        return Response
            .status(Response.Status.CREATED.getStatusCode())
            .entity(userRequest)
            .build();
    }

    @GET
    public Response listAllUsers() {
//        PanacheQuery<User> query = User.findAll(); // CTRL + Alt + V: Cria uma variável no Intellij; CTRL + Shift + N: Busca arquivo dentro do projeto
        PanacheQuery<User> query = repository.findAll();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id){
//        User user = User.findById(id);
        User user = repository.findById(id);

        if(user != null) {
//            user.delete();
            repository.delete(user);
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userData) {
//        User user = User.findById(id);
        User user = repository.findById(id);

        if(user != null) { // Detalhe: não é necessário colocar um repository.update() ou algo do tipo, porque o método já está anotado como Transactional (Auto-Commit). Ou seja, quando este método finalizar, ele vai commitar qualquer alteração que a classe User sofreu.
            user.setName(userData.getName());
            user.setAge(userData.getAge());
            return Response.noContent().build(); // O código correto para PUT é 204
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}

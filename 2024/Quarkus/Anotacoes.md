# Anotações dos comandos quarkus utilizados no maven

- Comando para inicializar a criação de um projeto quarkus. Se informar o comando até o create, ele pedirá os inputs durante o processo deste comando. Abaixo, o comando:
    > ./mvnw io.quarkus.platform:quarkus-maven-plugin:3.8.1:create \\  
    -DprojectGroupId=my-groupId \\  
    -DprojectArtifactId=my-artifactId

- Comando para compilar a aplicação e deixar em modo de desenvolvimento. O modo de desenvolvimento deixa o hot deploy ativado e podemos modificar o código e ele vai fazer o hot deploy ou hot swap, que é basicamente a atualização da aplicação enquanto a gente atualiza o código sem precisar derrubá-la. Abaixo, o comando:
    > ./mvnw compile quarkus:dev

- Comando para listar todas as dependências que podem ser utilizadas no Quarkus, organizadas pelo artifactIds e nome. Abaixo, o comando:
    > ./mvnw quarkus:list-extensions

- Comando para adicionar uma dependência que foi listada pelo comando anterior, através do seu respectivo artifactId. O -Dextensions permite adicionar mais que uma dependência de uma vez, separado por uma vírgula dentro das aspas e sem espaço. Abaixo os comandos:
    > ./mvnw quarkus:add-extension -Dextensions="hibernate-validator"

    > ./mvnw quarkus:add-extension -Dextensions="jdbc-h2,hibernate-orm,hibernate-orm-panache,resteasy-jsonb"
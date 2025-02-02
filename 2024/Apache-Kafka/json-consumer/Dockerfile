# Escolhendo JDK
FROM openjdk:17-alpine
# Criando variável que pegará tudo que estiver dentro da target e for um jar
ARG JAR_FILE=target/*.jar
# Copiando variável JAR_FILE par um pacote jar
COPY ${JAR_FILE} app.jar
# Criando cadeia de comandos para execução do jar criado
ENTRYPOINT ["java", "-jar", "app.jar"]
# Comando no terminal que gerará uma imagem do projeto: docker build -t allan542/payment-service:1.0.0 .
# Etapas do comando:
# -t: indicar o usuario-dockerhub/nome-projeto:versao
# .: indicar que o Dockerfile está na raíz do projeto
# sempre aumentar versão quando tiver alteração
# Comando de login antes de fazer o push para o DockerHub: docker login
# Comando para subir no DockerHub: docker push allan542/json-consumer:1.0.1
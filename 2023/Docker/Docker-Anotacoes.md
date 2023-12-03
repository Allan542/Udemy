## Anotações Docker
Docker é um software que permite a implantação de aplicações dentro de um repositório para que possa ser usado remotamente dentro de um ambiente local. Uma das vantagens do docker é possuir uma infraestrutura baseada em Cloud. Antes do Docker, Máquinas virtuais eram muito populares para instalar e rodar aplicações em um ambiente virtual. O problema com máquinas virtuais é que elas são muito pesadas pelo fato de ter dois Sistemas Operacionais operantes dentro de uma máquina e é onde o Docker entra, possibilitando instalar a Docker Engine de acordo com o SO para ser usado.

- Docker Image: Contêm todas as coisas que a sua aplicação necessita para rodar, desde o software certo, a versão correta para o Java, todas as packages sua aplicação necessita etc. Image é um template estático, um conjunto de bytes;

- Container Image: É uma versão rodável de uma certa imagem e, para a mesma imagem, é possível rodar múltiplos containers com versões iguais ou não. Image é como uma Classe. Container é como um Objeto;

- Docker Registry: Contêm um número de repositórios dentro do DockerHub;

- Docker Client: Onde é possível rodar os comandos do Docker;

- Docker Daemon: Quando algo é digitado para o Docker Client, ele envia de volta algo pelo Docker Daemon ou Docker Engine, para a execução do comando. Mesmo dentro do docker, ele ainda funciona como um tipo de arquitetura Cliente-Servidor. Ambos os Docker Client e Docker Daemon são instalados quando instalamos o Docker. Docker Daemon é responsável por gerenciar Local Images e é responsável por puxar algo do Image Registry ou mandar uma Image criada localmente para o Image Registry (remoto);

## Comandos Docker
- docker login: faz login no Docker Client para acesso ao Docker remoto (DockerHub);

- docker-compose up: Executa as configurações para a criação de um local image a partir de um arquivo YAML, no qual precisa estar nomeado como "docker-compose". O terminal de comandos precisa apontar para a pasta na qual o arquivo "docker-compose.yaml" está alocado;

- docker system df: Permite ver todas as coisas diferentes que o Docker Daemon gerencia, sendo Images, Containers, Local Volumes, Build Cache. Mostra informações como: "TYPE", "TOTAL", "ACTIVE", "SIZE", "RECLAIMABLE";

### Containers
- docker run (caminho/nome do repositorio):(tag/versão): Se a image não estiver baixada dentro do docker na máquina local, ele faz um pull (baixa a image) para que ela seja gere o container na máquina operante, porém, não permite o acesso ao container desta image. Se uma versão diferente é especificada de uma Image já existente, o comando puxará outra image localmente. Exemplo de comando: docker run -p 5001:5000 in28min/todo-rest-api-h2:0.0.1-SNAPSHOT. Apesar de puxar uma image, ele executa o container;
    - -p (portaDoHost{5001}):(portaDoContainer{5000}) ou **porta**: Configura a porta em que o host da máquina local pode acessar o container e a porta padrão na qual o container executa dentro do docker. Com isso, permite a máquina local que acesse o container pela porta definida na **portaDoHost**;
    - -d ou **detachedMode**: Desassocia o ciclo de vida do container. Apresenta o ID do container no qual está rodando, porém, não exibe mais os logs continuamente, não segue mais os logs do container;
    - --restart=(always/no): define a política de reinicialização da aplicação. Se especificado always, ele inicia a container especificada no run toda vez que o Docker Desktop é reiniciado. Se removido com *docker container prune*, a configuração também não existirá mais;
    - -m: Define o máximo de memória o container especificado pode usar. Exemplos: 512m, 1G;
    - --cpu-quota (valor da cota como um inteiro): Define a cota de CPU que pode ser usado pelo container especificado. Para 100% da CPU, o valor é 100000%, então 5% é 5000 ou 50%, 50000;

- docker logs (Container ID): Mostra alguns dos logs para a aplicação específica do ID do container;
    - -f ou **following**: Começa a seguir os logs da aplicação específica;

- docker container ls: Mostra todos os containers que estão rodando no momento com um conjunto de informações como: "CONTAINER ID", "IMAGE", "COMMAND", "CREATED", "STATUS", "PORTS", "NAMES";
    - a: Mostra todos os containers, incluindo os que não estão rodando;

- docker container stop (Container ID): paralisa a execução de um container pelo seu ID, o que faz com que ele não mostre no *docker container ls* sem o *-a*;

- docker container pause (Container ID): Pausa a execução de um container, não permitindo acessar o container e também, não mostra a execução de novos logs;

- docker container inspect (Container ID): Mostra com detalhes, algumas configurações do Container, alguns comandos que são necessários para rodar o container a partir de tal Image e outras informações que são necessárias;

- docker container prune: Remove todos os containers que estão parados;

- docker container stop (Container ID): Encerra a execução dentro de 10 segundos para que a aplicação encerre tudo corretamente. É chamado de SIGTERM, "graceful shutdown";

- docker container kill (Container ID): Encerra a execução da aplicação de forma agressiva, sem a possibilidade de um tempo para que tudo feche corretamente. Válido usar apenas em momentos em que a aplicação não encerra de forma casual. É chamado de SIGKILL, "immediately terminates the process";

- docker events: Mostra informações sobre a execução de eventos quando uma container sofre alguma ação, como executar, pausar, parar etc;

- docker top (Container ID): Mostra todos os os processos/comandos que estão rodando para o container especificado. Mostra informações como: "PID", "USER", "TIME", "COMMAND"";

- docker stats: Mostra todos os status de acordo com os containers que estão rodando. Mostra informações como: "CONTAINER ID", "NAME", "CPU %", "MEM USAGE / LIMIT", "MEM", "NET I/O", "BLOCK I/O", "PIDS";

### Images
- docker images: Mostra as images que foram puxadas do Docker Registry/Docker Hub para o ambiente local com algumas informações como: "REPOSITORY", "TAG", "IMAGE ID", "CREATED", "SIZE";

- docker tag (caminho/nome do repositorio):(tag/versão) (mesmo caminho/nome):(novaTag): Cria uma nova tag, porém da mesma image, com o mesmo Image ID, então no *docker image* mostra a mesma image com tags diferentes. Por exemplo, uma tag chamada **latest** aponta para a última versão do projeto, porém, não necessariamente precisa apontar para a última versão lançada, é apenas uma tag que não reconhece automaticamente a última versão;

- docker pull (caminho/nome do repositório): Quando não informada a tag, puxa sempre a tag padrão latest. Este comando, diferente do *docker run*, apenas puxa a imagem do repositório para o ambiente local sem executar o mesmo;

- docker search (caminho/nome do repositório): Procura pelo nome do repositório, a image que gostaria de buscar com alguns detalhes como, se é uma imagem oficial, ou seja, é um conjunto de repositórios que foram verificados pelo time do Docker como uma imagem oficial;

- docker image history (Image ID): Mostra alguns históricos no momento da criação da Image dentro de um caminho específico no Docker, com alguns comandos dentro de cada histórico que servem para serem executados quando um container é rodado;

- docker image inspect (Image ID): Mostra com detalhes, algumas configurações da Image, alguns comandos que são necessários para rodar o container a partir de tal Image e outras informações que são necessárias;

- docker image remove (Image ID): Remove uma image a partir do Image ID, porém, só remove local images e não remove do Docker Registry (remoto);

# Redis o que é
- Redis é um armazenamento de estrutura de dados de chave-valor de código aberto e na memória, também sendo possível armazenar dados em disco. Os principais casos de uso do Redis, incluem cache, gerenciamento de sessões, PUB/SUB e classificações.

## Problemas que o redis pode resolver
- Quer um software de cache: redis serve justamente para isso;
- Quem são os amigos que estão online: Uma informação que não necessariamente precisa estar num banco relacional;
- Qual o número de jogadores online: Num banco relacional, fazer um select count() no status de jogadores que estão online pode ser bastante custoso. Se colocada essa informação no redis, já que a mesma não necessariamente precisa ser armazenada para sempre, pode ajudar nesse problema de uma forma rápida e otimizada, porém será feita de uma forma mais complexa;
- Quem são os amigos em comum entre Clayton e Daniela: por causa da operação matemática de intersecção, o redis pode ajudar a resolver esse problema de uma forma rápida e otimizada;
- Como armazenamento temporário, que são excluídas automaticamente após um tempo de vida determinado;
- Precisa de modelo de assinatura, onde alguém publica, e um ou mais assinantes recebe a informação (pub/sub): A assinatura, eu acredito que precisa estar um banco relacional por ser um parâmetro de cadastro, mas o envio até um usuário pode ficar num cache para ser mostrado ao usuário numa outra sessão;
- First in first-out queues: Redis também pode servir como uma fila, até mesmo num sistema que exista para controle de fila, já que são dados voláteis;
- Agregar usuários por localização (Redis in Action, 146): são problemas simples, não recomendado usar para calcular a distância de uma rota para outra, apenas agrupar pessoas numa determinada localização;
- Seguidores, seguindo como em uma social media (Redis in Action, 190);

## Redis é escalável
Sim, ele é escalável, está preparado para cloud e tem várias formas de torná-lo escalável. 
Redis em máquinas diferentes, ou seja clusters diferentes, compartilham a informação entre as máquinas. Então se uma máquina cai, a outra passa a gravar. Isso permite um espalhamento de dados, balanceamento de carga, permitindo gravar mais dados sem necessariamente aumentar a memória.
Redis funciona com processo com master e slave. Sempre salva do dado no master e copia para o slave. Partindo desse princípio, você sempre vai poder ler os dados das duas instâncias, só que questão de gravação é sempre no master. Dá um balanceamento de carga na leitura, que normalmente é a maior carga porque é o que mais acontece. Pode colocar mais do que 1 nó slave. Um slave pode ter um slave, que também pode ter um slave e assim por diante.
Sentinel é um recurso atrelado a um failover. Failover significa que você tem um recurso que vai se recuperar de falhas automaticamente. Pra isso, é necessário atender algumas regras: a recomendação mínima é que se tenha no mínimo 3 instâncias do Redis. Se cair um master, a outra instância assume. Recomendado sempre números pares para decidir quem que é o master. Então Sentinel é o recurso do Redis para recuperação automática de falhas.

### Comandos do redis
- **redis-server**: inicia o servidor do redis;
- **redis-cli**: inicia o comando de cliente do redis. Esse cara já é um cliente que vem com o redis e que permite conectar no servidor, não sendo necessário confirmar um cliente e nem informar a porta de conexão;
package com.springbatch.simplepartitionerlocal.config;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// É necessário criar essa classe porque não existe um componente out of the box para particionar o processamento em apenas um arquivo. Tem apenas para múltiplos arquivos
@Component
public class ArquivoPartitioner implements Partitioner {

    @Value("${migracaoDados.totalRegistros}")
    public Integer totalRegistros;

    @Value("${migracaoDados.gridSize}")
    public Integer gridSize;

    // Ele recebe o gridSize para saber quantas partições serão criadas
    // Cada partição é criada com um identificador e um contexto de execução. As informações para saber o que a partição vai processar, vai entrar no ExecutionContext
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> map = new HashMap<>();
        for (int i = 0; i < gridSize; i++) {
            ExecutionContext context = new ExecutionContext();
            context.putInt("particao", i); // Definir as partições e seu respectivo index a partir do tamanho do gridSize e colocá-lo dentro do Map de contexto de execução. Informar quais linhas são responsabilidade do particionador para ler, quais linhas ele precisa
            map.put("partition" + i, context); // Esse nome aparece no metadados. No stepExecution, a gente vai ter um registro de StepExecution para cada partição, como se estivesse configurado multiplas steps de forma independente
        }
        return map;
    }

    // Cálculos para informar ao leitor, quantos registros serão lidos por vez, que neste caso, o limite é de 2000 registros, começando a partir do número seguinte da partição anterior. Ex.: a partição anterior para no 2000, a próxima vai começar no número de registro 2001 e assim por diante
    public int calcularPrimeiroItemleitura(Integer particao) {
        Integer indexPrimeiroItem = (particao * (totalRegistros / gridSize)); // Define a partir de que número de linha do registro a partição irá começar (Mas isso é no leitor)
        // 0 * 20000/10 = 0
        // 1 * 20000/10 = 2000
        return indexPrimeiroItem;
    }

    // Cálculo para definir o limite entre as partições
    public int getItensLimit() {
        return totalRegistros / gridSize;
    }

}

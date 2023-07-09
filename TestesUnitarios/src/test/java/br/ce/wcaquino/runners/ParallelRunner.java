package br.ce.wcaquino.runners;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerScheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Classe faz execução paralela de testes, usando um RunnerScheduler
public class ParallelRunner extends BlockJUnit4ClassRunner {
    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     *
     * @param klass
     * @throws InitializationError if the test class is malformed
     */
    public ParallelRunner(Class<?> klass) throws InitializationError {
        super(klass);
        setScheduler(new ThreadPool());
    }

}

// Classe que implementa RunnerScheduler, que é um agendador de tarefas
class ThreadPool implements RunnerScheduler {
    // Executor que serve para fazer as definições do cronograma da thread
    private ExecutorService executor;

    // Quantas threads serão usadas
    public ThreadPool(){
        executor = Executors.newFixedThreadPool(5);
    }

    @Override // Faz um envio da instância de runnable que bate aqui
    public void schedule(Runnable run) {
        executor.submit(run);
    }

    @Override // Executor é finalizado e desligado. Além disso, ele faz uma espera de 10 min até que tudo termine. Se terminar antes, ele encerra
    public void finished() {
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e){
            throw new RuntimeException();
        }
    }
}

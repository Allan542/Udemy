package com.springbatch.migracaodados.writer;

public class TesteThread2 {

    // recurso para estudo básico de threads: https://medium.com/swlh/understanding-java-threads-once-and-for-all-711f71e0ec1e

    public static void main(String[] args) {
        CounterTask task = new CounterTask();
        Thread counter1 = new Thread(task);
        Thread counter2 = new Thread(task);
        Thread counter3 = new Thread(task);
        Thread counter4 = new Thread(task);
        counter1.start();
        counter2.start();
        counter3.start();
        counter4.start();
    }
}

class CounterTask implements Runnable {
    private int counter;

    // A keyword syncronized assegura que tudo dentro do bloco será executado apenas se não há uma thread executando este bloco.
    // Isso é feito através do mutex o que bloqueia o acesso para o bloco delimitado (recurso). Neste exemplo, a chave é o próprio objeto
    // por possuir um contador acessado por todas as threas. O mutex sincroniza a impressão entre todas as threads, assegurando a ordem da contagem.
    // Pode ser rodado várias vezes e o resultado do contador vai ser o mesmo, mudando apenas a ordem das threads.
    @Override
    public void run() {
        synchronized (this) {
            counter++;
            System.out.println(Thread.currentThread().getName() + ": " + counter);
        }
    }
}
package com.springbatch.migracaodados;

public class TesteThread3 {
    public static void main(String[] args) {
        Data data = new Data();
//        data.setOutOfSync(true);
        Thread readData = new Thread(new ReadDataThread(data));
        Thread syncData = new Thread(new SyncDataThread(data));
        // DaemonThread são threads de baixa prioridade comparada as UserThreads. Sua única função é prover serviço para UserThreads
        // Uma vez que daemon threads são designadas para servir user threads e são necessárias apenas enquanto user threads estão rodando,
        // elas não evitam que a jvm pare uma vez que todas as user threads terminaram suas execuções.
        // É por isso que loops infinitos, que tipicamente existem em daemon threads, não irão causar problemas, porque qualquer código, incluindo blocos finally,
        // não serão executados uma vez que todas as user threads terminaram suas execuções. Por esta razão, daemon threads não são recomendadadas para I/O.
        // No entando tem exceções a esta regra. Codigos não tão bem feitos em threads daemon podem impedir a jvm de encerrar. Por exemplo, chamar Thread.join() numa
        // daemon thread que está executando pode bloquear o encerramento da aplicação
        syncData.setDaemon(true);

        readData.start();
        syncData.start();
    }
}

class ReadDataThread implements Runnable {
    private Data data;

    public ReadDataThread(Data data){
        super();
        this.data = data;
    }

    @Override
    public void run() {
        data.read();
    }
}

class SyncDataThread implements Runnable {
    private Data data;

    public SyncDataThread(Data data){
        super();
        this.data = data;
    }

    @Override
    public void run() {
        data.sync();
    }
}

class Data {
    private boolean outOfSync;
    private int value;

    public boolean isOutOfSync() {
        return outOfSync;
    }

    public void setOutOfSync(boolean outOfSync) {
        this.outOfSync = outOfSync;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public synchronized void sync(){
        System.out.println("Synchronizing data...");

        try{
            if (!outOfSync)
                this.wait(); // quando não é daemon, a thread espera aqui infinitamente e não tem como notificá-la para ser liberada. Só que essa thread não é necessária quando os dados estão sincronizados na thread read

            outOfSync = false;
            Thread.sleep(5000);
            System.out.println("Synchronized");
            this.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void read() {
        System.out.println("Reading data...");

        try {
            if (outOfSync)
                this.wait();

            Thread.sleep(2000);
            System.out.println("Data: " + value);
            this.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package com.springbatch.migracaodados.writer;

public class TesteThread {

    // recurso para estudo básico de threads: https://medium.com/swlh/understanding-java-threads-once-and-for-all-711f71e0ec1e

    public static void main(String[] args) {
        Thread task1 = new Thread(new PrintHelloTask("Task 1"));
        Thread task2 = new Thread(new PrintHelloTask("Task 2"));
        Thread task3 = new Thread(new PrintHelloTask("Task 3"));
        Thread task4 = new Thread(new PrintHelloTask("Task 4"));
        task1.start();
        task2.start();
        task3.start();
        task4.start();
    }
}

class PrintHelloTask implements Runnable {
    private String name;

    public PrintHelloTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            System.out.println(name + "foi interrompida!");
            e.printStackTrace();
        }
        System.out.println("Hello, " + name + "!");
    }

}
package com.springbatch.migracaodados;

import java.net.Socket;

public class TesteThread4 {
    public static void main(String[] args) {
        Race race = new Race();
        Thread runner1 = new Thread(new Runner(race, "Runner1"));
        Thread runner2 = new Thread(new Runner(race, "Runner2"));
        System.out.println("Starting...");
        runner1.start();
        runner2.start();
    }
}

class Runner implements Runnable {
    public Runner(Race race, String runner) {
        super();
        this.race = race;
        this.runner = runner;
    }

    public Race race;
    public String runner;

    @Override
    public void run() {
        System.out.println(runner + " running...");
        while (!race.isFinished()){
            race.setFinished(true);
            System.out.println("Winner: " + runner);
            return;
        }
        System.out.println(runner + " lost :(");
    }
}

class Race {
    private volatile boolean finished;

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
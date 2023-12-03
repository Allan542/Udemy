package com.springbatch.migracaodados;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Teste {
    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File("teste.txt");
        FileWriter fileWriter = new FileWriter(file);
        for (int i = 0; i < 10; i++) {
            if (i < 5) {
                Thread thread = new Thread(() -> {
                        int j = 0;
                        while(j < 100000){
                            try {
                                fileWriter.write("Oi" + "\n");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            j++;
                        }
                    });
                thread.start();
            } else {
                Thread thread = new Thread(() -> {
                    int j = 0;
                    while(j < 100000){
                        try {
                            fileWriter.write("Tchau" + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        j++;
                    }
                });
                thread.start();
            }
        }


        Thread.sleep(60000);
        fileWriter.close();

    }
}

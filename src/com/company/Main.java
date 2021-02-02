package com.company;

import java.util.ArrayList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static ArrayList<Process> processes = new ArrayList<Process>();

    public static void main(String[] args) {
        Memory memory = new Memory(6);

        Runnable p1 = new Process(1 , 2 , memory);
        Runnable p2 = new Process(2 , 2 , memory);
        Runnable p3 = new Process(3 , 2 , memory);
        Runnable p4 = new Process(4 , 2 , memory);


        ExecutorService pool = Executors.newFixedThreadPool(3);

        pool.execute(memory);
        pool.execute(p1);
        pool.execute(p2);
        pool.execute(p3);
        pool.execute(p4);
        
        pool.shutdown();
    }

}

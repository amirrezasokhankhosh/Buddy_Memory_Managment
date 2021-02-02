package com.company;

import java.util.ArrayList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static ArrayList<Process> processes = new ArrayList<Process>();
    static int number_of_processes = 10;
    static int size_of_memory = 1024;
    static Memory memory;

    public static void main(String[] args) {
        memory = new Memory(size_of_memory);
        for (int i = 0; i < number_of_processes; i++) {
            Process process = new Process(i + 1, 1, memory);
            processes.add(process);
        }
        ExecutorService pool = Executors.newFixedThreadPool(number_of_processes + 1);
        pool.execute(memory);
        for (Process process : processes) {
            pool.execute(process);
        }
        pool.shutdown();
    }

}

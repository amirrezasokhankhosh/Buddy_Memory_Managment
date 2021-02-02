package com.company;

class Process implements Runnable {
    private int p_id;
    private int size;
    private Memory memory;
    private long virtualMemory;

    public Process(int p_id, int size, Memory memory) {
        this.p_id = p_id;
        this.size = size;
        this.memory = memory;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("Process number " + p_id + " wants to call allocate for the " + (i + 1) + " time!");
            memory.allocate(p_id, size + i);
            System.out.println("Process number " + p_id + " going to sleep after allocate!");
            try {
                Thread.sleep(5000 * (i + 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Process number " + p_id + " wakes up after allocate!");
            memory.deallocate(p_id);
            System.out.println("Process number " + p_id + " going to sleep after deallocate!");
            try {
                Thread.sleep(5000 * (i + 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

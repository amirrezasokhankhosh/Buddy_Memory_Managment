package com.company;

class Process implements Runnable {
    private int p_id;
    private int size;
    private long virtualMemory;

    public Process(int p_id, int size) {
        this.p_id = p_id;
        this.size = size;
    }

    @Override
    public void run() {
        // allocate , deallocate
    }
}

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
            int num = (int) (Math.random() * 100);

            if ((num % 10) % 2 == 0) {
                memory.allocate(p_id, ((num / 10) + 1));
                try {
                    Thread.sleep(5000 * (i + 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                memory.deallocate(p_id);
                try {
                    Thread.sleep(3000 * (i + 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

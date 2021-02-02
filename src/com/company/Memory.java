package com.company;

import java.util.concurrent.locks.ReentrantLock;

public class Memory implements Runnable{
    private static int size;
    private ReentrantLock lock = new ReentrantLock();
    // list for empty memories
    // list for taken memories

    public Memory(int size) {
        this.size = size;

        // create those lists
    }

    public long allocate(int p_id, int size) {
        lock.lock();
        // allocate memory somehow
        lock.unlock();
        return 0;
    }

    public void deallocate(int p_id, long address) {
        lock.lock();
        // deallocate memory somehow
        lock.unlock();
    }

    private long calculateVirtualAddress(int number) {
        // calculate the virtual address for an allocated memory.
        return 0;
    }

    @Override
    public void run() {
        for(int i = 0 ; i < 10 ; i++){
            // show information about the memory
            try{
                Thread.sleep(5);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

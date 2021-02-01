package com.company;

public class Memory {
    private static int size;
    // list for empty memories
    // list for taken memories

    public Memory(int size) {
        this.size = size;

        // create those lists
    }

    public long allocate(int p_id, int size) {
        // allocate memory
        return 0;
    }

    public void deallocate(int p_id, long address) {
        // deallocate memory
    }

    private long calculateVirtualAddress(int number) {
        // calculate the virtual address for an allocated memory.
        return 0;
    }
}

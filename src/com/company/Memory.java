package com.company;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Memory implements Runnable {
    private static int size;
    private static int count_of_32;
    private static ReentrantReadWriteLock lock;
    private static ArrayList<Block> blocks;


    public Memory(int size) {
        this.size = size;
        count_of_32 = size / 32;
        lock = new ReentrantReadWriteLock();
        blocks = new ArrayList<Block>();
    }

    public int allocate(int p_id, int size){
        if(size > 32 || size < 1){
            return -1;
        }
        lock.writeLock().lock();
        System.out.println("Process number " + p_id + " got the lock!");
        joinNotUsedBlocks();
        if (hasMemory(p_id) == null) {
            int blockSize = calculateBlockSize(size);
            Block block = allocateBlock(p_id, size, blockSize);
            if (block.getSize() == 0) {
                System.out.println("MEMORY IS FULL!");
                System.out.println("Process number " + p_id + " released the lock!");
                lock.writeLock().unlock();
                return -1;
            }
            System.out.println("Process number " + p_id + " released the lock!");
            lock.writeLock().unlock();
            return 0;
        } else {
            if (checkForSpace(p_id, size)) {
                for (Block block : blocks) {
                    if (block.getP_id() == p_id) {
                        block.setUsedSize(block.getUsedSize() + size);
                        System.out.println("Process number " + p_id + " released the lock!");
                        lock.writeLock().unlock();
                        return 0;
                    }
                }
            } else {
                for (Block block : blocks) {
                    if (block.getP_id() == p_id) {
                        int sizeNow = block.getUsedSize();
                        block.setUsedSize(0);
                        block.setP_id(0);
                        block.setUsed(false);
                        int blockSize = calculateBlockSize(sizeNow + size);
                        Block newBlock = allocateBlock(p_id, size + sizeNow, blockSize);
                        if (block.getSize() == 0) {
                            System.out.println("MEMORY IS FULL!");
                            System.out.println("Process number " + p_id + " released the lock!");
                            lock.writeLock().unlock();
                            return -1;
                        }
                        System.out.println("Process number " + p_id + " released the lock!");
                        lock.writeLock().unlock();
                        return 0;
                    }
                }
            }
        }
        System.out.println("Process number " + p_id + " released the lock!");
        lock.writeLock().unlock();

        return -1;
    }

    public void deallocate(int p_id) {
        lock.writeLock().lock();
        System.out.println("Process number " + p_id + " got the lock for deallocate!");
        for (Block block : blocks) {
            if (block.getP_id() == p_id) {
                block.setUsedSize(0);
                block.setP_id(0);
                block.setUsed(false);
                break;
            }
        }
        joinNotUsedBlocks();
        System.out.println("Process number " + p_id + " released the lock after deallocate!");
        lock.writeLock().unlock();
    }


    private void joinNotUsedBlocks() {
        // join unused blocks with the same size;
        int checkSize = 1;
        ArrayList<Block> notUsedBlocks = new ArrayList<Block>();
        while (checkSize != 64) {
            for (Block block : blocks) {
                if (block.getSize() == checkSize && !block.isUsed()) {
                    notUsedBlocks.add(block);
                }
            }
            int countOfDoubles = (notUsedBlocks.size() / 2) % 10;
            for (int i = 0; i < countOfDoubles; i++) {
                blocks.remove(notUsedBlocks.get(i));
                blocks.remove(notUsedBlocks.get(i + 1));
                Block block = new Block(checkSize * 2, 0, false, 0);
                blocks.add(block);
            }
            checkSize = checkSize * 2;
            notUsedBlocks.clear();
        }
    }

    private Block hasMemory(int p_id) {
        for (Block block : blocks) {
            if (block.isUsed() && block.getP_id() == p_id) {
                return block;
            }
        }

        return null;
    }

    private Block allocateBlock(int p_id, int usedSize, int sizeOfBlock) {
        int checkSize = sizeOfBlock;
        while (true) {
            if (checkSize == 64) {
                if (count_of_32 != 0) {
                    Block block = new Block(32, 0, false, 0);
                    blocks.add(block);
                    checkSize = checkSize / 2;
                    count_of_32 = count_of_32 - 1;
                } else {
                    Block block = new Block(0, 0, false, 0);
                    return block;
                }

            } else {
                if (checkSize == sizeOfBlock) {
                    for (Block block : blocks) {
                        if (block.getSize() == sizeOfBlock && !block.isUsed()) {
                            block.setUsed(true);
                            block.setP_id(p_id);
                            block.setUsedSize(usedSize);
                            return block;
                        }
                    }
                    checkSize = checkSize * 2;
                } else {
                    boolean contains = false;
                    Block parentBlock = new Block(1, 0, true, 0);
                    for (Block block : blocks) {
                        if (block.getSize() == checkSize && !block.isUsed()) {
                            parentBlock = block;
                            contains = true;
                        }
                    }
                    if (!contains) {
                        checkSize = checkSize * 2;
                    } else {
                        blocks.remove(parentBlock);
                        Block childBlock1 = new Block(checkSize / 2, 0, false, 0);
                        Block childBlock2 = new Block(checkSize / 2, 0, false, 0);
                        blocks.add(childBlock1);
                        blocks.add(childBlock2);
                        checkSize = checkSize / 2;
                    }
                }
            }
        }
    }

    private int calculateBlockSize(int size) {
        if(isPowerOfTwo(size)){
            return size;
        }
        double log = (Math.log(size) / Math.log(2));
        int power = (int) (log % 10) + 1;
        return (int) (Math.pow(2, power));
    }

    private boolean isPowerOfTwo(int n)
    {
        if (n == 0)
            return false;

        while (n != 1) {
            if (n % 2 != 0)
                return false;
            n = n / 2;
        }
        return true;
    }

    private boolean checkForSpace(int p_id, int size) {
        for (Block block : blocks) {
            if (block.getP_id() == p_id) {
                int freeSpace = block.getSize() - block.getUsedSize();
                if (freeSpace >= size) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            lock.readLock().lock();
            System.out.println("\n***********************\n");
            for(Block block : blocks){
                System.out.println(block);
            }
            System.out.println("\n***********************\n");
            lock.readLock().unlock();
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

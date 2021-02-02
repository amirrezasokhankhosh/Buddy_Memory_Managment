package com.company;

public class Block {
    private int size;
    private int p_id;
    private boolean used;
    private int usedSize;

    public Block(int size, int p_id, boolean used , int usedSize) {
        this.size = size;
        this.p_id = p_id;
        this.used = used;
    }

    public int getSize() {
        return size;
    }

    public int getP_id() {
        return p_id;
    }

    public int getUsedSize() {
        return usedSize;
    }

    public boolean isUsed() {
        return used;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setUsedSize(int usedSize) {
        this.usedSize = usedSize;
    }

    @Override
    public String toString() {
        return "Block {" +
                " size = " + size +
                ", p_id = " + p_id +
                ", used = " + used +
                ", usedSize = " + usedSize +
                ", freeSpace = " + (size - usedSize) +
                " }";
    }
}

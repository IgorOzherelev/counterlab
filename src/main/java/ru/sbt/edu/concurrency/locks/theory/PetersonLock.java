package ru.sbt.edu.concurrency.locks.theory;

import ru.sbt.edu.concurrency.locks.ILock;

import static ru.sbt.edu.concurrency.util.TwoThreadIds.me;

public class PetersonLock implements ILock {
    private volatile boolean[] flag = new boolean[2];
    private volatile int victim;

    @Override
    public void lock() {
        int i = me();
        int j = 1 - i;
        flag[i] = true;
        victim = i;
        while(flag[j] && victim == i) {} // wait
    }


    @Override
    public void unlock() {
        int i = me();
        flag[i] = false;
    }
}

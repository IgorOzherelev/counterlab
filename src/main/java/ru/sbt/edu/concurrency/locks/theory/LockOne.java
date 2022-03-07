package ru.sbt.edu.concurrency.locks.theory;

import ru.sbt.edu.concurrency.locks.ILock;

import static ru.sbt.edu.concurrency.util.TwoThreadIds.me;
import static ru.sbt.edu.concurrency.util.TwoThreadIds.not;


public class LockOne implements ILock {
    // private final boolean[] flag = new boolean[2];
    private volatile boolean[] flag = new boolean[2];
    // thread-local index, 0 or 1

    @Override
    public void lock() {
        int i = me();
        int j = not(i);
        flag[i] = true;

        while(flag[j]) {} // wait
    }


    @Override
    public void unlock() {
        int i = me();
        flag[i] = false;
    }
}
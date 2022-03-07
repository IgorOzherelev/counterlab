package ru.sbt.edu.concurrency.locks.theory;

import ru.sbt.edu.concurrency.locks.ILock;

import static ru.sbt.edu.concurrency.util.TwoThreadIds.me;

public class LockZero implements ILock {
    private final boolean[] flag = new boolean[2];

    @Override
    public void lock() {
        int me = me();
        flag[me] = true;
    }


    @Override
    public void unlock() {
        flag[me()] = false;
    }
}
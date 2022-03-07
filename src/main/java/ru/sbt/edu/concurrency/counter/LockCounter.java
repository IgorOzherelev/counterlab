package ru.sbt.edu.concurrency.counter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCounter implements Counter {
    private long counter = 0;
    private final Lock lock = new ReentrantLock();

    @Override
    public void increment() {
        lock.lock();

        try {
            counter++;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long getValue() {
        return counter;
    }
}

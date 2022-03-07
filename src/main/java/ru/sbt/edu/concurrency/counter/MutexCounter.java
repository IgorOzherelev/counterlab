package ru.sbt.edu.concurrency.counter;

public class MutexCounter implements Counter {
    private volatile long count = 0;

    @Override
    public synchronized void increment() {
        count++;
    }

    @Override
    public synchronized long getValue() {
        return count;
    }
}

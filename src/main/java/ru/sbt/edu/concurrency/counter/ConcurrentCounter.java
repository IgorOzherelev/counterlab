package ru.sbt.edu.concurrency.counter;

import java.util.concurrent.Semaphore;

public class ConcurrentCounter implements Counter {
    private long counter = 0;
    private final Semaphore semaphore = new Semaphore(1, true);

    @Override
    public void increment() {
        try {
            semaphore.acquire();
            counter++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        semaphore.release();
    }

    @Override
    public long getValue() {
        return counter;
    }
}

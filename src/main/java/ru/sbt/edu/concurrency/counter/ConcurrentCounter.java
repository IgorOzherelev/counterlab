package ru.sbt.edu.concurrency.counter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;

public class ConcurrentCounter implements Counter {
    private long counter = 0;
    private final ConcurrentMap<String, Long> counterMap = new ConcurrentHashMap<>();
    private final Semaphore semaphore = new Semaphore(1, true); // обеспечен fairness

    @Override
    public void increment() {
        try {
            semaphore.acquire();
            counter++;
            counterMap.put("counter", counter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    @Override
    public long getValue() {
        return counterMap.get("counter");
    }
}

package ru.sbt.edu.concurrency.counter;

import static ru.sbt.edu.concurrency.util.TwoThreadIds.me;

public class MagicCounter implements Counter {
    private final long[] counters;

    public MagicCounter(int threadsNum) {
        this.counters = new long[threadsNum];
    }

    @Override
    public void increment() {
        int i = me();
        counters[i]++;
    }

    @Override
    public long getValue() {
        long result = 0;

        for (long counter : counters) {
            result += counter;
        }

        return result;
    }
}

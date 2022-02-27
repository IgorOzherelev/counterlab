package ru.sbt.edu.concurrency.locks;

import org.junit.Test;
import ru.sbt.edu.concurrency.counter.*;
import ru.sbt.edu.concurrency.locks.theory.LockOne;
import ru.sbt.edu.concurrency.locks.theory.LockTwo;
import ru.sbt.edu.concurrency.locks.theory.PetersonLock;
import ru.sbt.edu.concurrency.util.TwoThreadIds;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class ILockTest {
    @Test
    public void testLockOne() {
        ILock lock = new LockOne();
        Counter counter = new ILockCounter(lock);

        testCounter(counter, 100, 2);
    }

    @Test
    public void testLockTwo() {
        ILock lock = new LockTwo();
        Counter counter = new ILockCounter(lock);

        testCounter(counter, 100, 2);
    }

    @Test
    public void testPetersonLock()  {
        ILock lock = new PetersonLock();
        Counter counter = new ILockCounter(lock);
        //try: 1, 2, 10, 100, 1000
        testCounter(counter, 100, 2);
    }

    @Test
    public void testNaiveCounter()  {
        Counter counter = new SeqCounter();

        testCounter(counter, 1000, 2);
    }

    @Test
    public void testMutexCounter() {
        Counter counter = new MutexCounter();

        testCounter(counter, 1000, 3);
    }

    @Test
    public void testLockCounter() {
        Counter counter = new LockCounter();

        testCounter(counter, 1000, 4);
    }

    @Test
    public void testConcurrentCounter() {
        Counter counter = new ConcurrentCounter();

        testCounter(counter, 1000, 4);
    }

    @Test
    public void testMagicCounter() {
        int threadsNum = 4;
        Counter counter = new MagicCounter(threadsNum);

        testCounter(counter, 1000, threadsNum);
    }

    private void testCounter(Counter counter, long iters, int threadsNum) {
        Runnable increment = provideRunnable(counter, iters);

        Thread[] threads = new Thread[threadsNum];
        for (int i = 0; i < threadsNum; i++) {
            threads[i] = new Thread(increment);
        }

        Arrays.stream(threads).forEach(Thread::start);

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        checkCounter(counter, iters, threadsNum);
    }

    private void checkCounter(Counter counter, long iters, int threadsNum) {
        long count = counter.getValue();
        System.out.println("Counter value: " + count);
        assertEquals("Oops! Unexpected Behaviour!", iters * threadsNum, count);
    }

    private Runnable provideRunnable(Counter counter, long iters) {
        return () -> {
            System.out.println("threadId: " + TwoThreadIds.me());
            for (int i = 0; i < iters; i++) {
                counter.increment();
            }
        };
    }
}
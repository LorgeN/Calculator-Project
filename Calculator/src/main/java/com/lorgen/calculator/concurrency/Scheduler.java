package com.lorgen.calculator.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private ExecutorService executor;
    private ScheduledExecutorService scheduledExecutor;

    public Scheduler() {
        this.executor = Executors.newCachedThreadPool();
        this.scheduledExecutor = Executors.newScheduledThreadPool(3);
    }

    public void submit(Runnable runnable) {
        this.executor.submit(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void scheduleAtFixedRate(Runnable runnable, long initDelay, long amount, TimeUnit unit) {
        this.scheduledExecutor.scheduleAtFixedRate(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, initDelay, amount, unit);
    }

    public void schedule(Runnable runnable, long delay, TimeUnit unit) {
        this.scheduledExecutor.schedule(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, delay, unit);
    }
}

package com.lorgen.calculator.session;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.lorgen.calculator.Calculator;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Simple queue based implementation of multithreading, allowing each
 * session to operate on a single on-demand thread, using a queue of
 * {@link FutureTask future tasks}.
 */
public class SessionThread implements Runnable {

    private Session session;
    private boolean running;
    private Queue<FutureTask<?>> tasks;

    public SessionThread(Session session) {
        this.session = session;

        this.tasks = Queues.newConcurrentLinkedQueue();
    }

    public void start() {
        this.running = true;

        Calculator.getScheduler().submit(this);
    }

    public boolean isRunning() {
        return running;
    }

    public void kill() {
        this.running = false;
    }

    public <T> ListenableFuture<T> submit(Callable<T> callable) {
        ListenableFutureTask<T> task = ListenableFutureTask.create(callable);
        this.tasks.add(task);

        if (!this.isRunning()) {
            this.start();
        }

        return task;
    }

    @Override
    public void run() {
        this.session.setThreadId(Thread.currentThread().getId());

        while (this.running) {
            FutureTask<?> task = this.tasks.poll();
            if (task == null) {
                this.running = false;
                break;
            }

            task.run();
        }

        this.session.undefineThread();
    }
}

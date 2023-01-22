package com.github.rbaul.completablefutureinheritable;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ContextSavingForkJoinPool extends ForkJoinPool {

    @Override
    public <T> ForkJoinTask<T> submit(Callable<T> task) {
        return super.submit(InheritableLocalContext.wrapWithContext(task));
    }

    @Override
    public <T> ForkJoinTask<T> submit(Runnable task, T result) {
        return super.submit(InheritableLocalContext.wrapWithContext(task), result);
    }

    @Override
    public ForkJoinTask<?> submit(Runnable task) {
        return super.submit(InheritableLocalContext.wrapWithContext(task));
    }

    @Override
    public void execute(Runnable task) {
        super.execute(InheritableLocalContext.wrapWithContext(task));
    }
}
package com.github.rbaul.completablefutureinheritable;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class InheritableTaskExecutor extends ThreadPoolTaskExecutor {
	@Override
	public Future<?> submit(Runnable task) {
		return super.submit(InheritableLocalContext.wrapWithContext(task));
	}
	
	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return super.submit(InheritableLocalContext.wrapWithContext(task));
	}
	
	@Override
	public void execute(Runnable task) {
		super.execute(InheritableLocalContext.wrapWithContext(task));
	}
}

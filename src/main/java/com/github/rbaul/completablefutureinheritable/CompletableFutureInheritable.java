package com.github.rbaul.completablefutureinheritable;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;

public class CompletableFutureInheritable {
	
	private static final ThreadPoolTaskExecutor executor = new InheritableTaskExecutor();
	
	static {
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(1);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("GithubLookup-");
		executor.initialize();
	}
	
	/**
	 * Returns a new CompletableFuture that is asynchronously completed
	 * by a task running in the {@link ForkJoinPool#commonPool()} after
	 * it runs the given action.
	 *
	 * @param runnable the action to run before completing the
	 * returned CompletableFuture
	 * @return the new CompletableFuture
	 */
	public static CompletableFuture<Void> runAsync(Runnable runnable) {
		return CompletableFuture.runAsync(runnable, executor);
	}
	
	/**
	 * Returns a new CompletableFuture that is asynchronously completed
	 * by a task running in the {@link ForkJoinPool#commonPool()} with
	 * the value obtained by calling the given Supplier.
	 *
	 * @param supplier a function returning the value to be used
	 * to complete the returned CompletableFuture
	 * @param <U> the function's return type
	 * @return the new CompletableFuture
	 */
	public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
		return CompletableFuture.supplyAsync(supplier, executor);
	}
}

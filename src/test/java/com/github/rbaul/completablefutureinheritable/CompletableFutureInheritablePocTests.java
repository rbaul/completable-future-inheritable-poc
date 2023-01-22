package com.github.rbaul.completablefutureinheritable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class CompletableFutureInheritablePocTests {
	
	private final String someValue = "someValue";
	
	@Test
	@DisplayName("Completable Future - with InheritableThreadLocal")
	void runAsync_0() throws ExecutionException, InterruptedException {
		InheritableLocalContext.setValue(someValue);
		CompletableFuture.runAsync(() -> Assertions.assertNull(InheritableLocalContext.getValue())).get();
	}
	
	@Test
	@DisplayName("Completable Future Inheritable - with InheritableThreadLocal")
	void runAsync_1() throws ExecutionException, InterruptedException {
		InheritableLocalContext.setValue(someValue);
		CompletableFutureInheritable.runAsync(() -> Assertions.assertEquals(someValue, InheritableLocalContext.getValue())).get();
		String otherValue = "otherValue";
		InheritableLocalContext.setValue(otherValue);
		CompletableFutureInheritable.runAsync(() -> Assertions.assertEquals(otherValue, InheritableLocalContext.getValue())).get();
	}
	
	@Test
	@DisplayName("Completable Future Inheritable - with ThreadLocal")
	void runAsync_2() throws ExecutionException, InterruptedException {
		LocalContext.setValue(someValue);
		CompletableFutureInheritable.runAsync(() -> Assertions.assertNull(LocalContext.getValue())).get();
	}
	
	@Test
	@DisplayName("Completable Future - SupplyAsync - with InheritableThreadLocal")
	void supplyAsync_0() throws ExecutionException, InterruptedException {
		InheritableLocalContext.setValue(someValue);
		CompletableFuture<String> result = CompletableFuture.supplyAsync(InheritableLocalContext::getValue);
		Assertions.assertNull(result.get());
	}
	
	@Test
	@DisplayName("Completable Future Inheritable - SupplyAsync - with InheritableThreadLocal")
	void supplyAsync_1() throws ExecutionException, InterruptedException {
		InheritableLocalContext.setValue(someValue);
		CompletableFuture<String> result = CompletableFutureInheritable.supplyAsync(InheritableLocalContext::getValue);
		Assertions.assertEquals(someValue, result.get());
	}
	
	@Test
	@DisplayName("Completable Future Inheritable - SupplyAsync - with ThreadLocal")
	void supplyAsync_2() throws ExecutionException, InterruptedException {
		LocalContext.setValue(someValue);
		CompletableFuture<String> result = CompletableFutureInheritable.supplyAsync(LocalContext::getValue);
		Assertions.assertNull(result.get());
	}
	
	@Test
	@DisplayName("Thread - with InheritableThreadLocal")
	void thread_1() {
		InheritableLocalContext.setValue(someValue);
		Thread thread = new Thread(() -> Assertions.assertEquals(someValue, InheritableLocalContext.getValue()));
		thread.run();
	}
}

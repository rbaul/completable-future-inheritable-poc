package com.github.rbaul.completablefutureinheritable;

import java.util.concurrent.Callable;

/**
 * Here is the modified RequestContext class using InheritableThreadLocal variable, the data stored in the variable is copied over any child thread that is created.
 * InheritedThreadLocal context guarantees thread local variables are copied over to any child threads created by the request thread.
 *
 * This may not be true if we are using threadPools where threads are being re-used to perform tasks submitted to the threadpool.
 * The existing threads might not have right thread context info or might have a different context created by the previous request which might lead to inconsistencies.
 */
public class InheritableLocalContext {
	private static ThreadLocal<String> value = new InheritableThreadLocal<>();
	
	public static String getValue() {
		return value.get();
	}
	
	public static void setValue(String value) {
		InheritableLocalContext.value.set(value);
	}
	
	public static void clear() {
		value.set(null);
	}
	
	public static <T> Callable<T> wrapWithContext(Callable<T> task) {
		String value = InheritableLocalContext.getValue();
		return () -> {
			InheritableLocalContext.setValue(value);
			try {
				return task.call();
			} finally {
				// once the task is complete, clear thread state
				InheritableLocalContext.clear();
			}
		};
	}
	
	public static Runnable wrapWithContext(Runnable task) {
		String value = InheritableLocalContext.getValue();
		return () -> {
			InheritableLocalContext.setValue(value);
			try {
				task.run();
			} finally {
				// once the task is complete, clear thread state
				InheritableLocalContext.clear();
			}
		};
	}
	
}
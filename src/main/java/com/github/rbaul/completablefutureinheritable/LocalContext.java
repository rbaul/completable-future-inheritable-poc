package com.github.rbaul.completablefutureinheritable;

import java.util.concurrent.Callable;

public class LocalContext {
	private static ThreadLocal<String> value = new ThreadLocal<>();
	
	public static String getValue() {
		return value.get();
	}
	
	public static void setValue(String value) {
		LocalContext.value.set(value);
	}
	
	public static void clear() {
		value.set(null);
	}
	
	public static <T> Callable<T> wrapWithContext(Callable<T> task) {
		String value = LocalContext.getValue();
		return () -> {
			LocalContext.setValue(value);
			try {
				return task.call();
			} finally {
				// once the task is complete, clear thread state
				LocalContext.clear();
			}
		};
	}
	
	public static Runnable wrapWithContext(Runnable task) {
		String value = LocalContext.getValue();
		return () -> {
			LocalContext.setValue(value);
			try {
				task.run();
			} finally {
				// once the task is complete, clear thread state
				LocalContext.clear();
			}
		};
	}
	
}
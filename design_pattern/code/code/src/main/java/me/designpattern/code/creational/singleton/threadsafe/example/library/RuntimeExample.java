package me.designpattern.code.creational.singleton.threadsafe.example.library;

public class RuntimeExample {
	private static final Runtime runtime = Runtime.getRuntime();

	public static String maxMemory() {
		return String.format("Max memory : %s bytes", runtime.maxMemory());
	}
}

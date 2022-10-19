package me.designpattern.code.creational.singleton.threadsafe.safe.lazy;

public class SynchronizedSingleton {

	// public static final SynchronizedSingleton instance = getInstance(); // Eager initialization
	public static SynchronizedSingleton instance; // Lazy initialization

	private SynchronizedSingleton() {
	}

	public static synchronized SynchronizedSingleton getInstance() {
		if (instance == null)
			instance = new SynchronizedSingleton();
		return instance;
	}
}

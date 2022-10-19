package me.designpattern.code.creational.singleton.threadsafe.safe.lazy;

public class DoubleCheckedSingleton {
	private static volatile DoubleCheckedSingleton instance;

	public DoubleCheckedSingleton() {}

	public static DoubleCheckedSingleton getInstance() {
		if (instance == null) {
			synchronized (DoubleCheckedSingleton.class) {
				if (instance == null) {
					instance = new DoubleCheckedSingleton();
				}
			}
		}
		return instance;
	}
}

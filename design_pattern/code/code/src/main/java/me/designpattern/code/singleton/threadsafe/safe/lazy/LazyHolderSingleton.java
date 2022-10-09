package me.designpattern.code.singleton.threadsafe.safe.lazy;

public class LazyHolderSingleton {
	private LazyHolderSingleton() {}

	public static class LazyHolder {
		private static final LazyHolderSingleton INSTANCE = new LazyHolderSingleton();
	}

	public static LazyHolderSingleton getInstance() {
		return LazyHolder.INSTANCE;
	}
}

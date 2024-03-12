package me.designpattern.code.creational.singleton.threadsafe.safe.lazy;

import java.io.Serializable;

public class LazyHolderSingleton implements Serializable {
	protected LazyHolderSingleton() {}

	public static class LazyHolder {
		private static final LazyHolderSingleton INSTANCE = new LazyHolderSingleton();
	}

	public static LazyHolderSingleton getInstance() {
		return LazyHolder.INSTANCE;
	}
}

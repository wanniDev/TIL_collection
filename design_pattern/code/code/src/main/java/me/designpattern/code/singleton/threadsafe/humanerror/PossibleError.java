package me.designpattern.code.singleton.threadsafe.humanerror;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import me.designpattern.code.singleton.threadsafe.safe.lazy.LazyHolderSingleton;

public class PossibleError {
	public static LazyHolderSingleton getInstanceWithReflection() {
		Constructor<LazyHolderSingleton> constructor = null;
		try {
			constructor = LazyHolderSingleton.class.getDeclaredConstructor();
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		constructor.setAccessible(true);
		try {
			return constructor.newInstance();
		} catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}

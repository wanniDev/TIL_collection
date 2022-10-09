package me.designpattern.code.singleton.threadsafe.humanerror;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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

	public static LazyHolderSingleton getDeserializedInstance() {
		LazyHolderSingleton result;

		LazyHolderSingleton source = LazyHolderSingleton.getInstance();
		try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("lazyHolderSingleton.obj"))) {
			out.writeObject(source);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try (ObjectInput in = new ObjectInputStream(new FileInputStream("lazyHolderSingleton.obj"))) {
			result = (LazyHolderSingleton)in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		return result;
	}
}

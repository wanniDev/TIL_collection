package me.designpattern.code.creational.singleton.threadsafe.not;

public class SingletonClass {
	private static SingletonClass instance;

	private SingletonClass() {}

	public static SingletonClass getInstance() {
		if (instance == null)
			instance = new SingletonClass();
		return instance;
	}
}

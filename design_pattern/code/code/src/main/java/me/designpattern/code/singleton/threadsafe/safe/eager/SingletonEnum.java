package me.designpattern.code.singleton.threadsafe.safe.eager;

public enum SingletonEnum {
	INSTANCE;

	private int value;

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}

package me.designpattern.code.behavior.command;

public class Button {
	private final Light light;

	public Button(Light light) {
		this.light = light;
	}

	public void press() {
		light.on();
	}
}

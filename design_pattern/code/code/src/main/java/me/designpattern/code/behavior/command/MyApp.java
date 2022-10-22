package me.designpattern.code.behavior.command;

public class MyApp {
	private final Light light;

	public MyApp(Light light) {
		this.light = light;
	}

	public void press() {
		light.on();
	}

	public static void main(String[] args) {
		Button button = new Button(new Light());
		button.press();
		button.press();
		button.press();
	}
}
